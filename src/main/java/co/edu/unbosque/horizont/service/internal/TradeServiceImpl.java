package co.edu.unbosque.horizont.service.internal;

import co.edu.unbosque.horizont.dto.client.alpaca.AlpacaOrderResultDTO;
import co.edu.unbosque.horizont.dto.internal.OrdenRequestDTO;
import co.edu.unbosque.horizont.dto.internal.OrdenResponseDTO;
import co.edu.unbosque.horizont.dto.internal.OrdenResumenDTO;
import co.edu.unbosque.horizont.entity.*;
import co.edu.unbosque.horizont.repository.OrdenRepository;
import co.edu.unbosque.horizont.repository.PosicionRepository;
import co.edu.unbosque.horizont.repository.UsuarioRepository;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import co.edu.unbosque.horizont.service.client.alpaca.AlpacaClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TradeServiceImpl implements InterfaceTradeService {
    private final UsuarioRepository usuarioRepo;
    private final PosicionRepository posicionRepo;
    private final OrdenRepository ordenRepo;
    private final AlpacaClient alpaca;
    private static final Logger log = LoggerFactory.getLogger(TradeServiceImpl.class);

    public TradeServiceImpl(UsuarioRepository u, PosicionRepository p,
                            OrdenRepository o, AlpacaClient a) {
        this.usuarioRepo = u;
        this.posicionRepo = p;
        this.ordenRepo    = o;
        this.alpaca       = a;
    }

    @Override
    public OrdenResumenDTO previewOrder(Long usuarioId, OrdenRequestDTO req) {
        Usuario u = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        double price = alpaca.getLastPrice(req.getSimbolo());
        double total = price * req.getCantidad();

        if (req.getTipo() == TipoOrden.COMPRA && u.getBalanceUsd() < total) {
            throw new IllegalArgumentException("Fondos insuficientes");
        }
        if (req.getTipo() == TipoOrden.VENTA) {
            Posicion pos = posicionRepo.findByUsuarioAndSimbolo(u, req.getSimbolo())
                    .orElseThrow(() -> new IllegalArgumentException("No posee esa acción"));
            if (pos.getCantidad() < req.getCantidad()) {
                throw new IllegalArgumentException("Cantidad a vender excede las posiciones");
            }
        }

        return new OrdenResumenDTO(
                req.getSimbolo(),
                req.getCantidad(),
                req.getTipo(),
                price,
                total
        );
    }

    @Override
    @Transactional
    public OrdenResponseDTO executeOrder(Long usuarioId, OrdenRequestDTO req) {
        log.info("Iniciando ejecución de orden: usuarioId={}, simbolo={}, cantidad={}, tipo={}",
                usuarioId, req.getSimbolo(), req.getCantidad(), req.getTipo());

        OrdenResumenDTO resumen = previewOrder(usuarioId, req);
        log.info("Resumen generado: precioActual={}, totalEstimado={}",
                resumen.getPrecioActual(), resumen.getTotalEstimado());

        AlpacaOrderResultDTO result = alpaca.placeOrder(
                req.getSimbolo(),
                req.getCantidad(),
                req.getTipo()
        );
        log.info("Orden enviada a Alpaca. ID={}, precioEjecutado={}",
                result.getId(), result.getFilledAvgPrice());

        Usuario u = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        double executedPrice = result.getFilledAvgPrice();
        double total = executedPrice * req.getCantidad();

        if (req.getTipo() == TipoOrden.COMPRA) {
            u.setBalanceUsd(u.getBalanceUsd() - total);
            log.info("Balance actualizado tras COMPRA: nuevoBalance={}", u.getBalanceUsd());
        } else {
            u.setBalanceUsd(u.getBalanceUsd() + total);
            log.info("Balance actualizado tras VENTA: nuevoBalance={}", u.getBalanceUsd());

            Posicion posVenta = posicionRepo.findByUsuarioAndSimbolo(u, req.getSimbolo()).orElse(null);
            if (posVenta != null) {
                posVenta.setCantidad(posVenta.getCantidad() - req.getCantidad());
                posicionRepo.save(posVenta);
                log.info("Posición reducida: simbolo={}, nuevaCantidad={}", req.getSimbolo(), posVenta.getCantidad());
            } else {
                log.warn("No se encontró posición para reducir en venta: simbolo={}", req.getSimbolo());
            }
        }

        if (req.getTipo() == TipoOrden.COMPRA) {
            Posicion posCompra = posicionRepo.findByUsuarioAndSimbolo(u, req.getSimbolo())
                    .orElse(new Posicion(u, req.getSimbolo(), 0));
            posCompra.setCantidad(posCompra.getCantidad() + req.getCantidad());
            posicionRepo.save(posCompra);
            log.info("Posición aumentada o creada: simbolo={}, nuevaCantidad={}", req.getSimbolo(), posCompra.getCantidad());
        }

        usuarioRepo.save(u);
        log.info("Usuario actualizado en base de datos: id={}", u.getId());

        Orden orden = new Orden();
        orden.setUsuario(u);
        orden.setSimbolo(req.getSimbolo());
        orden.setCantidad(req.getCantidad());
        orden.setTipo(req.getTipo());
        orden.setPrecioUnitario(executedPrice);
        orden.setTotal(total);
        orden.setFecha(LocalDateTime.now());
        orden.setEstado(EstadoOrden.EJECUTADA);
        orden.setAlpacaOrderId(result.getId());
        ordenRepo.save(orden);
        log.info("Orden guardada en base de datos: id={}, simbolo={}, estado={}",
                orden.getId(), orden.getSimbolo(), orden.getEstado());

        return new OrdenResponseDTO(
                req.getSimbolo(),
                req.getCantidad(),
                req.getTipo(),
                executedPrice,
                total,
                orden.getEstado().name()
        );
    }

    @Override
    public List<OrdenResponseDTO> listOrders(Long usuarioId) {
        Usuario u = usuarioRepo.getReferenceById(usuarioId);
        return ordenRepo.findAllByUsuarioOrderByFechaDesc(u).stream()
                .map(o -> new OrdenResponseDTO(
                        o.getSimbolo(), o.getCantidad(), o.getTipo(),
                        o.getPrecioUnitario(), o.getTotal(), o.getEstado().name()
                ))
                .collect(Collectors.toList());
    }
}
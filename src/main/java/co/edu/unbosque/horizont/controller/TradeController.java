package co.edu.unbosque.horizont.controller;

import co.edu.unbosque.horizont.dto.internal.OrdenRequestDTO;
import co.edu.unbosque.horizont.dto.internal.OrdenResponseDTO;
import co.edu.unbosque.horizont.dto.internal.OrdenResumenDTO;
import co.edu.unbosque.horizont.service.internal.InterfaceTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

@RestController
@RequestMapping("/api/trade")
public class TradeController {

    private static final Logger log = LoggerFactory.getLogger(TradeController.class);

    private final InterfaceTradeService tradeService;

    @Autowired
    public TradeController(InterfaceTradeService tradeService) {
        this.tradeService = tradeService;
    }

    /**
     * Endpoint para generar vista previa de una orden antes de ejecutarla.
     */
    @PostMapping("/preview/{usuarioId}")
    public OrdenResumenDTO previewOrder(
            @PathVariable Long usuarioId,
            @RequestBody OrdenRequestDTO orden
    ) {
        log.info("Solicitando vista previa de orden para usuarioId={}, simbolo={}, tipo={}, cantidad={}",
                usuarioId, orden.getSimbolo(), orden.getTipo(), orden.getCantidad());

        if (orden.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }

        return tradeService.previewOrder(usuarioId, orden);
    }

    /**
     * Endpoint para ejecutar una orden.
     */
    @PostMapping("/execute/{usuarioId}")
    public OrdenResponseDTO executeOrder(
            @PathVariable Long usuarioId,
            @RequestBody OrdenRequestDTO orden
    ) {
        log.info("Ejecutando orden para usuarioId={}, simbolo={}, tipo={}, cantidad={}",
                usuarioId, orden.getSimbolo(), orden.getTipo(), orden.getCantidad());

        if (orden.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }

        return tradeService.executeOrder(usuarioId, orden);
    }

    /**
     * Endpoint para listar las órdenes ejecutadas por un usuario.
     */
    @GetMapping("/list/{usuarioId}")
    public List<OrdenResponseDTO> listOrders(@PathVariable Long usuarioId) {
        log.info("Listando órdenes para usuarioId={}", usuarioId);
        return tradeService.listOrders(usuarioId);
    }
}
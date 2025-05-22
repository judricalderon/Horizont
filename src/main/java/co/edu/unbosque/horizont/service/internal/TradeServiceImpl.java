package co.edu.unbosque.horizont.service.internal;

import co.edu.unbosque.horizont.dto.internal.OrdenRequestDTO;
import co.edu.unbosque.horizont.dto.internal.OrdenResponseDTO;
import co.edu.unbosque.horizont.dto.internal.OrdenResumenDTO;
import co.edu.unbosque.horizont.entity.*;
import co.edu.unbosque.horizont.repository.OrdenRepository;
import co.edu.unbosque.horizont.repository.PosicionRepository;
import co.edu.unbosque.horizont.repository.UsuarioRepository;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.horizont.service.client.alpaca.AlpacaClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TradeServiceImpl implements InterfaceTradeService{


    @Override
    public OrdenResumenDTO previewOrder(Long usuarioId, OrdenRequestDTO req) {
        return null;
    }

    @Override
    public OrdenResponseDTO executeOrder(Long usuarioId, OrdenRequestDTO req) {
        return null;
    }

    @Override
    public List<OrdenResponseDTO> listOrders(Long usuarioId) {
        return List.of();
    }
}

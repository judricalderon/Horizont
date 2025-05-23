package co.edu.unbosque.horizont.service.internal;

import co.edu.unbosque.horizont.dto.internal.OrdenRequestDTO;
import co.edu.unbosque.horizont.dto.internal.OrdenResponseDTO;
import co.edu.unbosque.horizont.dto.internal.OrdenResumenDTO;

import java.util.List;

public interface InterfaceTradeService {
    OrdenResumenDTO previewOrder(Long usuarioId, OrdenRequestDTO req);
    OrdenResponseDTO executeOrder(Long usuarioId, OrdenRequestDTO req);
    List<OrdenResponseDTO> listOrders(Long usuarioId);
}

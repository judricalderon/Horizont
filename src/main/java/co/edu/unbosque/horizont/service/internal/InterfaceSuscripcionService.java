package co.edu.unbosque.horizont.service.internal;

import co.edu.unbosque.horizont.dto.internal.SuscripcionRequestDTO;
import co.edu.unbosque.horizont.dto.internal.SuscripcionResponseDTO;

import java.util.List;

public interface InterfaceSuscripcionService {

    SuscripcionResponseDTO crearSuscripcion(SuscripcionRequestDTO dto);

    SuscripcionResponseDTO obtenerSuscripcionPorId(Long id);

    List<SuscripcionResponseDTO> listarSuscripciones();

    SuscripcionResponseDTO actualizarSuscripcion(Long id, SuscripcionRequestDTO dto);

    void eliminarSuscripcion(Long id);
}

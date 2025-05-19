package co.edu.unbosque.horizont.service.internal;

import co.edu.unbosque.horizont.dto.internal.SuscripcionRequestDTO;
import co.edu.unbosque.horizont.dto.internal.SuscripcionResponseDTO;

import java.util.List;

/**
 * Interfaz que define los servicios del dominio interno relacionados con la gestión
 * de suscripciones de usuarios.
 *
 * Proporciona operaciones para crear, consultar, actualizar y eliminar suscripciones,
 * así como listarlas en forma de DTOs.
 */

public interface InterfaceSuscripcionService {

    /**
     * Crea una nueva suscripción a partir de los datos proporcionados.
     *
     * @param dto objeto que contiene la información necesaria para crear la suscripción
     * @return DTO con los datos de la suscripción creada
     */

    SuscripcionResponseDTO crearSuscripcion(SuscripcionRequestDTO dto);

}

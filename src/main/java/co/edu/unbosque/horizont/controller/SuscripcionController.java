package co.edu.unbosque.horizont.controller;

import co.edu.unbosque.horizont.dto.internal.SuscripcionRequestDTO;
import co.edu.unbosque.horizont.dto.internal.SuscripcionResponseDTO;
import co.edu.unbosque.horizont.service.internal.InterfaceSuscripcionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Controlador REST que expone endpoints relacionados con la gestión de suscripciones internas.
 *
 * Permite la creación de suscripciones en el sistema a partir de un DTO que representa la solicitud.
 *
 * Ruta base: <code>/api/suscripciones</code>
 */

@RestController
@RequestMapping("/api/suscripciones")
public class SuscripcionController {

    private final InterfaceSuscripcionService suscripcionService;

    /**
     * Constructor que inyecta el servicio de suscripciones utilizado para realizar operaciones
     * relacionadas con la lógica de negocio de suscripciones.
     *
     * @param suscripcionService servicio que gestiona las operaciones sobre suscripciones
     */

    public SuscripcionController(InterfaceSuscripcionService suscripcionService) {
        this.suscripcionService = suscripcionService;
    }
    /**
     * Crea una nueva suscripción en el sistema a partir de los datos proporcionados en el cuerpo de la solicitud.
     *
     * @param dto objeto que contiene la información necesaria para crear la suscripción
     * @return respuesta HTTP con el objeto de respuesta creado, incluyendo detalles de la suscripción
     */

    @PostMapping
    public ResponseEntity<SuscripcionResponseDTO> crearSuscripcion(@RequestBody SuscripcionRequestDTO dto) {
        SuscripcionResponseDTO creada = suscripcionService.crearSuscripcion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

}

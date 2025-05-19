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

    /**
     * Crea una nueva suscripción con los datos proporcionados en el cuerpo de la solicitud.
     *
     * @param dto objeto {@link SuscripcionRequestDTO} con los datos de la nueva suscripción
     * @return {@link ResponseEntity} con la suscripción creada y código HTTP 201 (Created)
     */
    @PostMapping
    public ResponseEntity<SuscripcionResponseDTO> crearSuscripcion(@RequestBody SuscripcionRequestDTO dto) {
        SuscripcionResponseDTO creada = suscripcionService.crearSuscripcion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }


    /**
     * Obtiene una suscripción por su identificador único.
     *
     * @param id ID de la suscripción a consultar
     * @return {@link ResponseEntity} con la suscripción encontrada o HTTP 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<SuscripcionResponseDTO> obtenerSuscripcion(@PathVariable Long id) {
        SuscripcionResponseDTO respuesta = suscripcionService.obtenerSuscripcionPorId(id);
        if (respuesta != null) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Lista todas las suscripciones registradas en el sistema.
     *
     * @return {@link ResponseEntity} con la lista de suscripciones
     */
    @GetMapping
    public ResponseEntity<List<SuscripcionResponseDTO>> listarSuscripciones() {
        return ResponseEntity.ok(suscripcionService.listarSuscripciones());
    }

    /**
     * Actualiza una suscripción existente con los datos proporcionados.
     *
     * @param id  ID de la suscripción a actualizar
     * @param dto objeto {@link SuscripcionRequestDTO} con los datos actualizados
     * @return {@link ResponseEntity} con la suscripción actualizada o HTTP 404 si no se encuentra
     */
    @PutMapping("/{id}")
    public ResponseEntity<SuscripcionResponseDTO> actualizarSuscripcion(
            @PathVariable Long id,
            @RequestBody SuscripcionRequestDTO dto) {
        SuscripcionResponseDTO actualizada = suscripcionService.actualizarSuscripcion(id, dto);
        if (actualizada != null) {
            return ResponseEntity.ok(actualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina una suscripción por su ID.
     *
     * @param id ID de la suscripción a eliminar
     * @return {@link ResponseEntity} con código HTTP 204 (No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSuscripcion(@PathVariable Long id) {
        suscripcionService.eliminarSuscripcion(id);
        return ResponseEntity.noContent().build();
    }
}

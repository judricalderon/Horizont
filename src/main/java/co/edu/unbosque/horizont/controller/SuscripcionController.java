package co.edu.unbosque.horizont.controller;

import co.edu.unbosque.horizont.dto.internal.SuscripcionRequestDTO;
import co.edu.unbosque.horizont.dto.internal.SuscripcionResponseDTO;
import co.edu.unbosque.horizont.service.internal.InterfaceSuscripcionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con las suscripciones.
 * <p>
 * Proporciona endpoints para crear, obtener, actualizar, eliminar y listar suscripciones.
 * Usa {@link InterfaceSuscripcionService} para delegar la lógica de negocio.
 */
@RestController
@RequestMapping("/api/suscripciones")
public class SuscripcionController {


    private final InterfaceSuscripcionService suscripcionService;

    /**
     * Constructor para inyectar el servicio de suscripciones.
     *
     * @param suscripcionService servicio que contiene la lógica para gestionar suscripciones
     */
    public SuscripcionController(InterfaceSuscripcionService suscripcionService) {
        this.suscripcionService = suscripcionService;
    }

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

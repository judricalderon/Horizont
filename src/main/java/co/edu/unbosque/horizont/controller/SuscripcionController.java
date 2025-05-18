package co.edu.unbosque.horizont.controller;

import co.edu.unbosque.horizont.dto.internal.SuscripcionRequestDTO;
import co.edu.unbosque.horizont.dto.internal.SuscripcionResponseDTO;
import co.edu.unbosque.horizont.service.internal.InterfaceSuscripcionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suscripciones")
public class SuscripcionController {

    private final InterfaceSuscripcionService suscripcionService;

    public SuscripcionController(InterfaceSuscripcionService suscripcionService) {
        this.suscripcionService = suscripcionService;
    }

    @PostMapping
    public ResponseEntity<SuscripcionResponseDTO> crearSuscripcion(@RequestBody SuscripcionRequestDTO dto) {
        SuscripcionResponseDTO creada = suscripcionService.crearSuscripcion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuscripcionResponseDTO> obtenerSuscripcion(@PathVariable Long id) {
        SuscripcionResponseDTO respuesta = suscripcionService.obtenerSuscripcionPorId(id);
        if (respuesta != null) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<SuscripcionResponseDTO>> listarSuscripciones() {
        return ResponseEntity.ok(suscripcionService.listarSuscripciones());
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSuscripcion(@PathVariable Long id) {
        suscripcionService.eliminarSuscripcion(id);
        return ResponseEntity.noContent().build();
    }
}

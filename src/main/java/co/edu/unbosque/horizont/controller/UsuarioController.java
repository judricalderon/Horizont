package co.edu.unbosque.horizont.controller;

import co.edu.unbosque.horizont.dto.internal.UsuarioDTO;
import co.edu.unbosque.horizont.service.internal.InterfaceUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final InterfaceUsuarioService usuarioService;

    @Autowired
    public UsuarioController(InterfaceUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Endpoint JSON para registrar un nuevo usuario y crear la cuenta Alpaca.
     * POST /usuarios/registrar
     * Content-Type: application/json
     */
    @PostMapping("/registrar")
    public ResponseEntity<UsuarioDTO> registrarUsuario(@RequestBody UsuarioDTO dto) {
        UsuarioDTO respuesta = usuarioService.registrarUsuarioDesdeDTO(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    /**
     * Endpoint para registrar vía formulario x-www-form-urlencoded (legacy).
     * POST /usuarios/form-registro
     */
    @PostMapping(value = "/form-registro", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<String> registrarUsuarioForm(@ModelAttribute UsuarioDTO dto) {
        usuarioService.registrarUsuarioDesdeDTO(dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Registro exitoso. Revisa tu correo para confirmar el código.");
    }

    /**
     * Endpoint para confirmar el código OTP según idUsuario.
     * POST /usuarios/confirmar?idUsuario={id}&codigo={codigo}
     */
    @PostMapping("/confirmar")
    public ResponseEntity<String> confirmarCodigo(@RequestParam Long idUsuario,
                                                  @RequestParam String codigo) {
        boolean confirmado = usuarioService.verificarCodigo(idUsuario, codigo);
        if (confirmado) {
            return ResponseEntity.ok("Código confirmado. Usuario activado.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Código inválido o expirado.");
        }
    }
}

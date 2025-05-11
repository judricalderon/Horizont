package co.edu.unbosque.horizont.controller;

import co.edu.unbosque.horizont.dto.internal.UsuarioDTO;
import co.edu.unbosque.horizont.exception.EmailAlreadyExistsException;
import co.edu.unbosque.horizont.service.internal.InterfaceUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Controlador REST para operaciones relacionadas con usuarios.
 * Proporciona endpoints para registro de nuevos usuarios, registro a través de formularios
 * y confirmación de códigos OTP enviados por correo.
 */
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    /**
     * Servicio de usuario que implementa la lógica de negocio.
     */
    private final InterfaceUsuarioService usuarioService;

    /**
     * Crea una instancia de {@link UsuarioController} con el servicio inyectado.
     *
     * @param usuarioService servicio de usuario para manejar operaciones de registro y verificación
     */
    @Autowired
    public UsuarioController(InterfaceUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Endpoint JSON para registrar un nuevo usuario y crear la cuenta en Alpaca.
     * <p>
     * Método: POST<br>
     * Ruta: /usuarios/registrar<br>
     * Content-Type: application/json<br>
     *
     * @param dto datos del usuario recibidos en el cuerpo de la petición
     * @return {@link ResponseEntity} con el usuario creado y código HTTP adecuado:
     *         <ul>
     *           <li>201 CREATED: Registro exitoso</li>
     *           <li>409 CONFLICT: Correo ya registrado o violación de integridad</li>
     *           <li>502 BAD_GATEWAY: Error en la API de Alpaca</li>
     *           <li>500 INTERNAL_SERVER_ERROR: Error inesperado</li>
     *         </ul>
     */
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestBody UsuarioDTO dto) {
        try {
            UsuarioDTO respuesta = usuarioService.registrarUsuarioDesdeDTO(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);

        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error de integridad de datos: " + e.getMessage());

        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body("Error en la API de Alpaca: " + e.getStatusText());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado: " + e.getMessage());
        }
    }

    /**
     * Endpoint legacy para registrar un usuario mediante formulario.
     * <p>
     * Método: POST<br>
     * Ruta: /usuarios/form-registro<br>
     * Consumes: application/x-www-form-urlencoded<br>
     *
     * @param dto datos del usuario mapeados desde los campos del formulario
     * @return {@link ResponseEntity} con mensaje de éxito o error:
     *         <ul>
     *           <li>202 ACCEPTED: Registro exitoso</li>
     *           <li>409 CONFLICT: Correo ya registrado o violación de integridad</li>
     *           <li>502 BAD_GATEWAY: Error en la API de Alpaca</li>
     *           <li>500 INTERNAL_SERVER_ERROR: Error inesperado</li>
     *         </ul>
     */
    @PostMapping(value = "/form-registro", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<String> registrarUsuarioForm(@ModelAttribute UsuarioDTO dto) {
        try {
            usuarioService.registrarUsuarioDesdeDTO(dto);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body("Registro exitoso. Revisa tu correo para confirmar el código.");

        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error de integridad de datos: " + e.getMessage());

        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body("Error en la API de Alpaca: " + e.getStatusText());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado: " + e.getMessage());
        }
    }

    /**
     * Endpoint para confirmar el código OTP enviado al usuario.
     * <p>
     * Método: POST<br>
     * Ruta: /usuarios/confirmar<br>
     * Parametros: idUsuario, codigo<br>
     *
     * @param idUsuario ID del usuario registrado
     * @param codigo código OTP recibido por correo
     * @return {@link ResponseEntity} con mensaje:
     *         <ul>
     *           <li>200 OK: Código válido y usuario activado</li>
     *           <li>400 BAD_REQUEST: Código inválido o expirado</li>
     *         </ul>
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

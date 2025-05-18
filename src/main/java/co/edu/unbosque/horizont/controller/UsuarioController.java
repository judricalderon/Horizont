package co.edu.unbosque.horizont.controller;

import co.edu.unbosque.horizont.dto.internal.UsuarioDTO;

import co.edu.unbosque.horizont.exception.EmailAlreadyExistsException;

import co.edu.unbosque.horizont.service.client.alpaca.AlpacaClient;
import co.edu.unbosque.horizont.service.internal.InterfaceUsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import co.edu.unbosque.horizont.dto.internal.LoginRequestDTO;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;
import java.util.Properties;


/**
 * Controlador REST que maneja las operaciones relacionadas con los usuarios.
 *
 * Esta clase expone endpoints HTTP para gestionar usuarios, incluyendo su registro.
 */


/**
 * Controlador REST para operaciones relacionadas con usuarios.
 * Proporciona endpoints para:
 *   - Registro JSON de nuevos usuarios (POST /usuarios/registrar)
 *   - Registro legacy por formulario (POST /usuarios/form-registro)
 *   - Confirmación de código OTP enviado por correo (POST /usuarios/confirmar)
 *
 * Incorpora además un ModelMapper y un AlpacaClient por si se desea mapear DTOs
 * o invocar directamente la API de Alpaca desde el controlador.
 */

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {


    private final InterfaceUsuarioService usuarioService;

    /**
     * Constructor para inyectar dependencias necesarias en el controlador.
     *
     * @param usuarioService servicio de usuarios que implementa la lógica de negocio
     * @param modelMapper objeto para realizar conversiones entre DTOs y entidades
     * @param alpacaClient cliente para interactuar con la API externa de Alpaca
     * @param usuarioService1 instancia específica del servicio de usuario (se utiliza como final)
     */

    /**
     * Inyección de dependencias:
     * @param usuarioService servicio con la lógica de registro y verificación

     */
    @Autowired
    public UsuarioController(InterfaceUsuarioService usuarioService) {
        this.usuarioService = usuarioService;

    }

    /**
     * Endpoint JSON para registrar un nuevo usuario y crear la cuenta en Alpaca.
     * Método: POST
     * Ruta: /usuarios/registrar
     *
     * @param dto datos del usuario en formato JSON
     * @return 201 CREATED + UsuarioDTO, o el código de error correspondiente
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
     * Método: POST
     * Ruta: /usuarios/form-registro
     * Consumes: application/x-www-form-urlencoded
     *
     * @param dto datos del usuario mapeados desde campos de formulario
     * @return 202 ACCEPTED con mensaje, o el código de error correspondiente
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
     * Método: POST
     * Ruta: /usuarios/confirmar
     *
     * @param idUsuario ID del usuario registrado
     * @param codigo    código OTP recibido por correo
     * @return 200 OK si el código es válido, 400 BAD_REQUEST si no lo es
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
    // --------------------------------------
    // LOGIN JSON
    // POST /usuarios/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO req) {
        return usuarioService.login(req.getCorreo(), req.getPassword())
                .map(u -> Map.of(
                        "id", u.getId(),
                        "nombre", u.getNombre(),
                        "correo", u.getCorreo(),
                        "esPremium", u.isEsPremium()
                ))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

}

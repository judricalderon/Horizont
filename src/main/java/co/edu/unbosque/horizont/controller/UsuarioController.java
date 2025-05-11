package co.edu.unbosque.horizont.controller;

import co.edu.unbosque.horizont.dto.internal.UsuarioDTO;
import co.edu.unbosque.horizont.service.client.alpaca.AlpacaClient;
import co.edu.unbosque.horizont.service.internal.InterfaceUsuarioService;
import co.edu.unbosque.horizont.service.internal.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * Controlador REST que maneja las operaciones relacionadas con los usuarios.
 *
 * Esta clase expone endpoints HTTP para gestionar usuarios, incluyendo su registro.
 */

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    /**
     * Constructor para inyectar dependencias necesarias en el controlador.
     *
     * @param usuarioService servicio de usuarios que implementa la lógica de negocio
     * @param modelMapper objeto para realizar conversiones entre DTOs y entidades
     * @param alpacaClient cliente para interactuar con la API externa de Alpaca
     * @param usuarioService1 instancia específica del servicio de usuario (se utiliza como final)
     */

    public UsuarioController(InterfaceUsuarioService usuarioService, ModelMapper modelMapper, AlpacaClient alpacaClient, UsuarioService usuarioService1) {
        this.usuarioService = usuarioService1;

    }
    /**
     * Endpoint HTTP POST para registrar un nuevo usuario.
     *
     * @param dto objeto {@link UsuarioDTO} que contiene los datos del usuario a registrar
     * @return {@link ResponseEntity} con el {@link UsuarioDTO} del usuario registrado y código de estado HTTP 200
     */
    @PostMapping("/registrar")
    public ResponseEntity<UsuarioDTO> registrarUsuario(@RequestBody UsuarioDTO dto) {
        UsuarioDTO respuesta = usuarioService.registrarUsuarioDesdeDTO(dto);
        return ResponseEntity.ok(respuesta);
    }
}

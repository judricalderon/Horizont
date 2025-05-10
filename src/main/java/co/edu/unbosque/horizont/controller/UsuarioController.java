package co.edu.unbosque.horizont.controller;

import co.edu.unbosque.horizont.dto.client.alpaca.AlpacaAccountDTO;
import co.edu.unbosque.horizont.dto.internal.UsuarioDTO;
import co.edu.unbosque.horizont.service.client.AlpacaClient;
import co.edu.unbosque.horizont.service.internal.InterfaceUsuarioService;
import co.edu.unbosque.horizont.service.internal.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;


    public UsuarioController(InterfaceUsuarioService usuarioService, ModelMapper modelMapper, AlpacaClient alpacaClient, UsuarioService usuarioService1) {
        this.usuarioService = usuarioService1;

    }

    @PostMapping("/registrar")
    public ResponseEntity<UsuarioDTO> registrarUsuario(@RequestBody UsuarioDTO dto) {
        UsuarioDTO respuesta = usuarioService.registrarUsuarioDesdeDTO(dto);
        return ResponseEntity.ok(respuesta);
    }
}

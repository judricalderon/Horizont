package co.edu.unbosque.horizont.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController; 

import co.edu.unbosque.horizont.dto.client.alpaca.AlpacaAccountDTO;
import co.edu.unbosque.horizont.service.client.AlpacaClient;

@RestController
@RequestMapping("/alpaca")
public class AlpacaController {

    private final AlpacaClient alpacaClient;

    public AlpacaController(AlpacaClient alpacaClient) {
        this.alpacaClient = alpacaClient;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AlpacaAccountDTO dto) {
        return alpacaClient.register(dto);
    }
}

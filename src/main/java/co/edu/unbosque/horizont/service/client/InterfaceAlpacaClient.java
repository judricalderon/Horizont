package co.edu.unbosque.horizont.service.client;

import co.edu.unbosque.horizont.dto.client.alpaca.AlpacaAccountDTO;
import org.springframework.http.ResponseEntity;

public interface InterfaceAlpacaClient {

    ResponseEntity<String> crearCuenta(AlpacaAccountDTO cuenta);
}

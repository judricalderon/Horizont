package co.edu.unbosque.horizont.service.client;

import co.edu.unbosque.horizont.dto.client.alpaca.AlpacaAccountDTO;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AlpacaClient {
    private final RestTemplate restTemplate;
    private final String ALPACA_URL = "https://broker-api.sandbox.alpaca.markets/v1/accounts"; // URL de sandbox para pruebas

    public AlpacaClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public ResponseEntity<String> crearCuenta(AlpacaAccountDTO cuenta) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(cuenta);
            System.out.println("JSON enviado a Alpaca:");
            System.out.println(json);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("APCA-API-KEY-ID", "TU_API_KEY");
            headers.set("APCA-API-SECRET-KEY", "TU_API_SECRET");

            HttpEntity<AlpacaAccountDTO> request = new HttpEntity<>(cuenta, headers);

            return restTemplate.postForEntity(ALPACA_URL, request, String.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al serializar JSON de la cuenta Alpaca: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear cuenta en Alpaca: " + e.getMessage());
        }
    }


}

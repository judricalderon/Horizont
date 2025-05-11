package co.edu.unbosque.horizont.service.client.alpaca;

import co.edu.unbosque.horizont.dto.client.alpaca.AlpacaAccountDTO;
import co.edu.unbosque.horizont.service.client.InterfaceAlpacaClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class AlpacaClient implements InterfaceAlpacaClient {

    @Value("${Alpaca_api_key}")
    private String apiKey;

    @Value("${Alpaca_api_secret}")
    private String apiSecret;

    private final RestTemplate restTemplate;
    private final String ALPACA_URL = "https://broker-api.sandbox.alpaca.markets/v1/accounts";

    public AlpacaClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }
@Override
    public ResponseEntity<String> crearCuenta(AlpacaAccountDTO cuenta) {
        try {
            // Convertir a JSON para revisión (opcional, útil en debugging)
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(cuenta);
            System.out.println("JSON enviado a Alpaca:");
            System.out.println(json);

            // Configurar headers
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(apiKey, apiSecret);
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Armar petición
            HttpEntity<AlpacaAccountDTO> request = new HttpEntity<>(cuenta, headers);

            // Hacer POST
            ResponseEntity<String> response = restTemplate.postForEntity(ALPACA_URL, request, String.class);

            // 💡 NUEVO: imprimir respuesta de Alpaca
            System.out.println("Respuesta HTTP Alpaca: " + response.getStatusCode());
            System.out.println("Body de la respuesta Alpaca: " + response.getBody());

            return response;
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
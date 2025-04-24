package co.edu.unbosque.horizont.service.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders; 
import org.springframework.http.MediaType; 
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service; 
import org.springframework.web.client.RestTemplate;

import co.edu.unbosque.horizont.dto.client.alpaca.AlpacaAccountDTO;

@Service
public class AlpacaClient {
    private final RestTemplate restTemplate;
    private final String ALPACA_URL = "https://api.alpaca.markets/v2/account";

    public AlpacaClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public ResponseEntity<String> register(AlpacaAccountDTO accountDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("APCA-API-KEY-ID", "TU_API_KEY");
        headers.set("APCA-API-SECRET-KEY", "TU_API_SECRET");

        HttpEntity<AlpacaAccountDTO> request = new HttpEntity<>(accountDTO, headers);
        return restTemplate.postForEntity(ALPACA_URL, request, String.class);
    }
}

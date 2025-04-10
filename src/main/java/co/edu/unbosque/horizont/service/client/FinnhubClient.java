package co.edu.unbosque.horizont.service.client;

import co.edu.unbosque.horizont.dto.client.finnhub.QuoteDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FinnhubClient implements InterfaceFinnhubClient {

    @Value("${finnhub.api.url}")
    private String apiUrl;

    @Value("${finnhub.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public FinnhubClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Override
    public QuoteDTO getQuote(String symbol) {
        String url = String.format("%s/quote?symbol=%s&token=%s", apiUrl, symbol, apiKey);
        return restTemplate.getForObject(url, QuoteDTO.class);
    }
}

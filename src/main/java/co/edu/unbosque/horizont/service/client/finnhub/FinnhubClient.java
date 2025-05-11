package co.edu.unbosque.horizont.service.client.finnhub;

import co.edu.unbosque.horizont.dto.client.finnhub.QuoteDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Cliente para consumir la API de Finnhub y obtener datos de cotizaciones en tiempo real.
 *
 * Esta clase utiliza {@link RestTemplate} para realizar peticiones HTTP a Finnhub,
 * recuperando información financiera basada en símbolos de activos.
 */
@Component
public class FinnhubClient implements InterfaceFinnhubClient {

    /** URL base de la API de Finnhub, configurada en las propiedades de la aplicación. */
    @Value("${finnhub.api.url}")
    private String apiUrl;

    /** Clave de autenticación para acceder a la API de Finnhub, configurada en las propiedades de la aplicación. */
    @Value("${finnhub.api.key}")
    private String apiKey;

    /** Cliente HTTP utilizado para realizar las peticiones REST. */
    private final RestTemplate restTemplate;
    /**
     * Constructor que inicializa el {@link RestTemplate} utilizando un {@link RestTemplateBuilder}.
     *
     * @param builder objeto usado para construir instancias de RestTemplate de forma flexible.
     */
    public FinnhubClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }
    /**
     * Obtiene la cotización en tiempo real de un activo financiero a partir de su símbolo.
     *
     * Realiza una solicitud HTTP a la API de Finnhub utilizando el símbolo especificado
     * y devuelve los datos encapsulados en un objeto {@link QuoteDTO}.
     *
     * @param symbol símbolo del activo financiero (por ejemplo, "AAPL" o "GOOGL").
     * @return un objeto {@link QuoteDTO} con la información de la cotización del activo.
     */
    @Override
    public QuoteDTO getQuote(String symbol) {
        String url = String.format("%s/quote?symbol=%s&token=%s", apiUrl, symbol, apiKey);
        return restTemplate.getForObject(url, QuoteDTO.class);
    }
}

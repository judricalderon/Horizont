package co.edu.unbosque.horizont.service.client.finnhub;

import co.edu.unbosque.horizont.dto.client.finnhub.QuoteDTO;
import co.edu.unbosque.horizont.dto.client.finnhub.HistoryDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import co.edu.unbosque.horizont.service.internal.HistoryCacheInterface;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Cliente para consumir la API de Finnhub y obtener datos de cotizaciones en tiempo real
 * y datos históricos de precios.
 *
 * Esta clase utiliza {@link RestTemplate} para realizar peticiones HTTP a Finnhub,
 * recuperando información financiera basada en símbolos de activos.
 */
@Component
public class FinnhubClient implements InterfaceFinnhubClient {

    /** URL base de la API de Finnhub, configurada en las propiedades de la aplicación. */
    @Value("${finnhub.api.url}")
    private String apiUrl;
    private final HistoryCacheInterface historyCache;

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
    public FinnhubClient(RestTemplateBuilder builder, HistoryCacheInterface historyCache) {
        this.restTemplate = builder.build();
        this.historyCache = historyCache;
    }

    /**
     * Obtiene la cotización en tiempo real de un activo financiero a partir de su símbolo.
     *
     * @param symbol símbolo del activo financiero (por ejemplo, "AAPL" o "GOOGL").
     * @return un objeto {@link QuoteDTO} con la información de la cotización del activo.
     */
    @Override
    public QuoteDTO getQuote(String symbol) {
        String url = String.format("%s/quote?symbol=%s&token=%s", apiUrl, symbol, apiKey);
        return restTemplate.getForObject(url, QuoteDTO.class);
    }

    /**
     * Obtiene datos de velas (candles) para un símbolo dentro de un rango de tiempo y resolución.
     *
     * @param symbol     ticker (por ej. "AAPL").
     * @param resolution intervalo o tipo de vela ("1", "5", "15", "D", "W", etc.).
     * @param from       timestamp Unix de inicio (segundos).
     * @param to         timestamp Unix de fin (segundos).
     * @return un objeto {@link HistoryDTO} con timestamps y precios de cierre.
     */

    @Override
    public HistoryDTO getStockCandles(String symbol, String resolution, long from, long to) {
        String cacheKey = symbol + "-" + resolution + "-" + from + "-" + to;

        if (historyCache.contains(cacheKey)) {
            return historyCache.get(cacheKey);
        }

        String url = String.format(
                "%s/stock/candle?symbol=%s&resolution=%s&from=%d&to=%d&token=%s",
                apiUrl, symbol, resolution, from, to, apiKey
        );

        try {
            HistoryDTO response = restTemplate.getForObject(url, HistoryDTO.class);

            if (response != null && "ok".equalsIgnoreCase(response.getStatus())) {
                historyCache.save(cacheKey, response);
            }

            return response != null ? response : HistoryDTO.empty();
        } catch (HttpClientErrorException.Forbidden ex) {
            System.err.println("Acceso prohibido al obtener historial de " + symbol + ": " + ex.getMessage());
        } catch (HttpClientErrorException ex) {
            System.err.println("Error HTTP al obtener historial de " + symbol + ": " + ex.getStatusCode());
        } catch (Exception ex) {
            System.err.println("Error inesperado al obtener historial de " + symbol + ": " + ex.getMessage());
        }

        return HistoryDTO.empty();
    }

}
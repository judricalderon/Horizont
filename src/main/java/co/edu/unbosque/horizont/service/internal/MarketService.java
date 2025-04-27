package co.edu.unbosque.horizont.service.internal;

import co.edu.unbosque.horizont.dto.client.finnhub.QuoteWithSymbolDTO;
import co.edu.unbosque.horizont.service.client.InterfaceFinnhubClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
/**
 * Servicio para gestionar operaciones relacionadas con el mercado financiero.
 *
 * Este servicio permite obtener cotizaciones en tiempo real de activos financieros
 * a través de la API de Finnhub.
 */
@Service
public class MarketService implements InterfaceMarketService {

    private final InterfaceFinnhubClient finnhubClient;
    /**
     * Constructor que inyecta el cliente de Finnhub.
     *
     * @param finnhubClient cliente utilizado para consumir la API de cotizaciones de Finnhub.
     */
    public MarketService(InterfaceFinnhubClient finnhubClient) {
        this.finnhubClient = finnhubClient;
    }
    /**
     * Obtiene cotizaciones en tiempo real para una lista de símbolos de activos financieros.
     *
     * Para cada símbolo proporcionado, consulta la API de Finnhub y encapsula los resultados
     * en objetos {@link QuoteWithSymbolDTO}.
     *
     * @param symbols lista de símbolos de activos financieros (por ejemplo, "AAPL", "GOOGL").
     * @return lista de {@link QuoteWithSymbolDTO} conteniendo las cotizaciones en tiempo real.
     */
    @Override
    public List<QuoteWithSymbolDTO> getRealtimeQuotes(List<String> symbols) {
        return symbols.stream()
                .map(symbol -> new QuoteWithSymbolDTO(symbol, finnhubClient.getQuote(symbol)))
                .collect(Collectors.toList());
    }
}

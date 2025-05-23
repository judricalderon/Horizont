package co.edu.unbosque.horizont.service.internal;

import co.edu.unbosque.horizont.dto.client.finnhub.HistoryDTO;
import co.edu.unbosque.horizont.dto.client.finnhub.QuoteWithSymbolDTO;
import co.edu.unbosque.horizont.service.client.finnhub.InterfaceFinnhubClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar operaciones relacionadas con el mercado financiero.
 *
 * Este servicio permite obtener cotizaciones en tiempo real y datos históricos
 * de activos financieros a través de la API de Finnhub.
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

    /**
     * Obtiene el histórico de intradía para un símbolo y resolución dada.
     *
     * @param symbol     ticker (por ej. "AAPL").
     * @param resolution intervalo en minutos (p.ej. 60).
     * @return {@link HistoryDTO} con timestamps y precios de cierre.
     */
    @Override
    public HistoryDTO getIntradayHistory(String symbol, int resolution) {
        long to = Instant.now().getEpochSecond();
        long from = to - 24 * 60 * 60; // últimas 24 horas
        String res = String.valueOf(resolution);
        return finnhubClient.getStockCandles(symbol, res, from, to);
    }

    /**
     * Obtiene el histórico del símbolo para distintos periodos:
     * - 1D: intradía con resolución 5m
     * - 1W: 1 semana con velas diarias
     * - 1M: 1 mes con velas diarias
     * - 1A: 1 año con velas semanales
     *
     * @param symbol    ticker (por ej. "AAPL").
     * @param timeframe "1D","1W","1M" o "1A".
     * @return {@link HistoryDTO} con timestamps y precios de cierre.
     */
    @Override
    @Cacheable(value = "stockHistoryCache", key = "#symbol + '_' + #timeframe")
    public HistoryDTO getHistoryByTimeframe(String symbol, String timeframe) {
        long to = Instant.now().getEpochSecond();
        long from;
        String resolution;

        switch (timeframe) {
            case "1D":
                from = to - 24 * 60 * 60;
                resolution = "5"; // 5 minutos
                break;
            case "1W":
                from = to - 7 * 24 * 60 * 60;
                resolution = "D"; // diario
                break;
            case "1M":
                from = to - 30 * 24 * 60 * 60;
                resolution = "D"; // diario
                break;
            case "1A":
                from = to - 365 * 24 * 60 * 60;
                resolution = "W"; // semanal
                break;
            default:
                throw new IllegalArgumentException("Timeframe inválido: " + timeframe);
        }

        return finnhubClient.getStockCandles(symbol, resolution, from, to);
    }
}

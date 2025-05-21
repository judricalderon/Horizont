package co.edu.unbosque.horizont.service.internal;

import co.edu.unbosque.horizont.dto.client.finnhub.QuoteWithSymbolDTO;
import co.edu.unbosque.horizont.dto.client.finnhub.HistoryDTO;

import java.util.List;

/**
 * Interfaz para el servicio de mercado financiero.
 *
 * Define las operaciones relacionadas con la obtención de cotizaciones en tiempo real
 * y datos históricos para activos financieros basados en sus símbolos.
 */
public interface InterfaceMarketService {

    /**
     * Obtiene cotizaciones en tiempo real para una lista de símbolos de activos financieros.
     *
     * @param symbols lista de símbolos (por ejemplo, "AAPL", "GOOGL") para los cuales se desean obtener cotizaciones.
     * @return lista de objetos {@link QuoteWithSymbolDTO} con los datos de cotización actuales.
     */
    List<QuoteWithSymbolDTO> getRealtimeQuotes(List<String> symbols);

    /**
     * Obtiene el histórico de intradía para un símbolo y resolución dada.
     *
     * @param symbol     ticker (por ej. "AAPL").
     * @param resolution intervalo en minutos (p.ej. 60).
     * @return {@link HistoryDTO} con timestamps y precios de cierre.
     */
    HistoryDTO getIntradayHistory(String symbol, int resolution);

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
    HistoryDTO getHistoryByTimeframe(String symbol, String timeframe);
}

package co.edu.unbosque.horizont.service.internal;

import co.edu.unbosque.horizont.dto.client.finnhub.QuoteWithSymbolDTO;

import java.util.List;
/**
 * Interfaz para el servicio de mercado financiero.
 *
 * Define las operaciones relacionadas con la obtención de cotizaciones en tiempo real
 * para activos financieros basados en sus símbolos.
 */
public interface InterfaceMarketService {
    /**
     * Obtiene cotizaciones en tiempo real para una lista de símbolos de activos financieros.
     *
     * @param symbols lista de símbolos (por ejemplo, "AAPL", "GOOGL") para los cuales se desean obtener cotizaciones.
     * @return lista de objetos {@link QuoteWithSymbolDTO} con los datos de cotización actuales.
     */
    List<QuoteWithSymbolDTO> getRealtimeQuotes(List<String> symbols);
}
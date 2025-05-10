package co.edu.unbosque.horizont.service.client.finnhub;

import co.edu.unbosque.horizont.dto.client.finnhub.QuoteDTO;
/**
 * Interfaz para el cliente de consumo de la API de Finnhub.
 *
 * Define la operación para obtener cotizaciones de activos financieros en tiempo real.
 */
public interface InterfaceFinnhubClient {
    /**
     * Obtiene la cotización actual de un activo financiero dado su símbolo.
     *
     * @param symbol símbolo del activo financiero (por ejemplo, "AAPL" o "GOOGL").
     * @return un objeto {@link QuoteDTO} que contiene los datos de la cotización.
     */
QuoteDTO getQuote(String symbol);
}
/**
 * Data Transfer Objects (DTOs) para consumo de la API de Finnhub.
 *
 * <p>Este paquete contiene las clases que modelan la respuesta directa de la API de Finnhub,
 * específicamente datos financieros relacionados con cotizaciones de acciones.
 *
 * <p>Los DTOs reflejan fielmente la estructura del JSON que entrega Finnhub, sin adaptaciones o transformaciones,
 * y están diseñados exclusivamente para el consumo de datos (lectura).
 *
 * <p>Actualmente los principales DTOs incluyen:
 * <ul>
 *   <li>{@code QuoteDTO}: Representa datos como precio actual, precio máximo, mínimo, apertura y cierre previo de una acción.</li>
 *   <li>{@code SymbolDTO}: Representa los símbolos de acciones disponibles en el mercado.</li>
 * </ul>
 *
 *
 * @version 1.0
 * @since 2025
 */package co.edu.unbosque.horizont.dto.client.finnhub;

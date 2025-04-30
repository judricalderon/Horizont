/**
 * Data Transfer Objects (DTOs) de Horizont.
 *
 * <p>Este paquete contiene las clases de transferencia de datos utilizadas para transportar
 * información relacionada con acciones financieras, incluyendo precios y símbolos, tanto de fuentes externas
 * (como la API de Finnhub) como de procesamiento interno.
 *
 * <p>Actualmente los principales DTOs incluyen:
 * <ul>
 *   <li>{@code QuoteDTO}: Representa las cotizaciones de acciones en tiempo real.</li>
 *   <li>{@code SymbolDTO}: Representa los símbolos de acciones disponibles.</li>
 * </ul>
 *
 * <p>Subpaquetes:
 * <ul>
 *   <li>{@code client.finnhub}: DTOs que manejan datos obtenidos de la API externa de Finnhub.</li>
 *   <li>{@code internal}: DTOs usados para el procesamiento interno dentro de la aplicación.</li>
 * </ul>
 *
 * <p>Todos los DTOs son simples estructuras de datos sin lógica de negocio ni validaciones avanzadas.
 *
 * @version 1.0
 * @since 2025
 */
package co.edu.unbosque.horizont.dto;
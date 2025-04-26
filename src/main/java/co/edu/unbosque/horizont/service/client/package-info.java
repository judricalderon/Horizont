/**
 * Clientes externos para consumo de APIs de terceros en Horizont.
 *
 * <p>Este paquete contiene las implementaciones de clientes que interactúan con servicios externos,
 * consumiendo información financiera de APIs públicas o privadas.
 *
 * <p>Actualmente incluye:
 * <ul>
 *   <li>{@code FinnhubClient}: Cliente para la API de Finnhub, encargado de obtener cotizaciones de acciones en tiempo real.</li>
 *   <li>{@code InterfaceFinnhubClient}: Interfaz que define las operaciones de consumo de datos desde Finnhub.</li>
 * </ul>
 *
 * <p>Los clientes en este paquete se encargan exclusivamente de consumir y retornar datos,
 * sin realizar procesamiento o transformación interna.
 *
 *
 * @version 1.0
 * @since 2025
 */
package co.edu.unbosque.horizont.service.client;
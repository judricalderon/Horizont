/**
 * Clientes externos para consumo de APIs de terceros en Horizont.
 *
 * <p>Este paquete contiene las implementaciones de clientes que interactúan con servicios externos,
 * consumiendo información financiera o gestionando datos de usuario a través de APIs públicas o privadas.</p>
 *
 * <p>Actualmente incluye:</p>
 * <ul>
 *   <li>{@code FinnhubClient} – Cliente para la API de Finnhub, encargado de obtener cotizaciones de acciones en tiempo real.</li>
 *   <li>{@code InterfaceFinnhubClient} – Interfaz que define las operaciones de consumo de datos desde Finnhub.</li>
 *   <li>{@link co.edu.unbosque.horizont.service.client.alpaca.AlpacaClient} – Cliente que realiza solicitudes para la creación de cuentas de usuario en la API de Alpaca.</li>
 *   <li>{@link co.edu.unbosque.horizont.service.client.alpaca.InterfaceAlpacaClient} – Interfaz que define las operaciones soportadas por el cliente Alpaca.</li>
 * </ul>
 *
 * <p>Los clientes en este paquete se encargan exclusivamente de consumir y retornar datos,
 * delegando cualquier lógica de negocio a servicios internos. Esto permite mantener una arquitectura
 * desacoplada y fácilmente testeable.</p>
 *
 * @version 1.1
 * @since 2025
 */
package co.edu.unbosque.horizont.service.client;

/**
 * Proporciona servicios para la integración con la API de Alpaca Broker.
 *
 * <p>Este paquete contiene componentes encargados de gestionar la comunicación HTTP con Alpaca,
 * incluyendo la creación de cuentas de usuario mediante solicitudes autenticadas.</p>
 *
 * <p>Clases principales:</p>
 * <ul>
 *     <li>{@link co.edu.unbosque.horizont.service.client.alpaca.AlpacaClient} – Implementación del cliente que
 *     realiza llamadas REST a la API de Alpaca para registrar cuentas de usuario utilizando objetos
 *     {@link co.edu.unbosque.horizont.dto.client.alpaca.AlpacaAccountDTO}.</li>
 *
 *     <li>{@link co.edu.unbosque.horizont.service.client.alpaca.InterfaceAlpacaClient} – Interfaz que define el contrato
 *     para las operaciones relacionadas con Alpaca, permitiendo flexibilidad y desacoplamiento.</li>
 * </ul>
 *
 * <p>Estas clases utilizan {@code RestTemplate} para enviar solicitudes HTTP con autenticación básica,
 * facilitando la integración con sistemas externos de forma controlada y reutilizable.</p>
 */
package co.edu.unbosque.horizont.service.client.alpaca;
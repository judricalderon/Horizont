/**
 * Servicios internos de la aplicación Horizont.
 *
 * <p>Este paquete contiene la lógica de negocio central de la aplicación, incluyendo el procesamiento
 * y la gestión de datos relacionados con cotizaciones de acciones y listas de seguimiento (watchlists).
 *
 * <p>Los servicios internos implementan interfaces para promover una arquitectura limpia y desacoplada,
 * facilitando la inversión de dependencias y el mantenimiento del sistema.
 *
 * <p>Actualmente incluye:
 * <ul>
 *   <li>{@code MarketService} y {@code InterfaceMarketService}: Gestión y procesamiento de cotizaciones de acciones.</li>
 *   <li>{@code WatchlistService} y {@code InterfaceWatchlistService}: Manejo de listas de seguimiento de acciones.</li>
 * </ul>
 *
 * <p>Estos servicios realizan procesamiento de datos, como filtrado, mapeo y preparación de respuestas,
 * sin delegarlo a capas externas.
 *
 *
 *
 * @version 1.0
 * @since 2025
 */
package co.edu.unbosque.horizont.service.internal;
/**
 * Controladores REST de Horizont.
 *
 * <p>Este paquete contiene los controladores que exponen endpoints HTTP para
 * consultar información financiera, como cotizaciones de acciones, utilizando
 * los servicios de la API de Finnhub.
 *
 * <p>Actualmente, el controlador principal es:
 * <ul>
 *   <li>{@code MarketController}: Maneja solicitudes relacionadas con la obtención de precios en tiempo real de acciones.</li>
 * </ul>
 *
 * <p>Los controladores están anotados con {@code @RestController} y siguen el patrón REST, recibiendo
 * y respondiendo peticiones bajo la ruta base {@code /api/market}.
 *
 * @see co.edu.unbosque.horizont.service
 *
 * @version 1.0
 * @since 2025
 */
package co.edu.unbosque.horizont.controller;
/**
 * Manejo de excepciones de la aplicación Horizont.
 *
 * <p>Este paquete contiene las clases destinadas a interceptar, procesar y estandarizar
 * el manejo de errores dentro de la aplicación.
 *
 * <p>Actualmente incluye:
 * <ul>
 *   <li>{@code BaseResponse}: Plantilla estándar para las respuestas de error HTTP, incluyendo mensaje, código y detalles opcionales.</li>
 *   <li>{@code ValidationExceptionHandler}: Clase que centraliza la gestión de excepciones usando {@code @ControllerAdvice}, manejando actualmente {@code IllegalArgumentException}.</li>
 * </ul>
 *
 * <p>Este paquete está preparado para crecer en el futuro, incorporando nuevos tipos de excepciones personalizadas
 * a medida que se amplíen las funcionalidades del proyecto.
 *
 * @version 1.0
 * @since 2025
 */
package co.edu.unbosque.horizont.exception;
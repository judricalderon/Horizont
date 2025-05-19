package co.edu.unbosque.horizont.service.client.stripe;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
/**
 * Interfaz que define los servicios de integración con Stripe para el manejo de suscripciones.
 *
 * Proporciona métodos para crear sesiones de pago y cancelar suscripciones activas
 * mediante la API de Stripe.
 */
public interface InterfaceStripeSubscriptionService {
    /**
     * Crea una sesión de pago (Checkout Session) en Stripe para el usuario especificado
     * y el tipo de plan elegido.
     *
     * @param usuarioId ID del usuario que desea suscribirse
     * @param tipoPlan tipo de plan de suscripción (por ejemplo: "mensual", "anual")
     * @return objeto {@link Session} de Stripe que contiene la URL para redirigir al cliente
     * @throws StripeException si ocurre un error al comunicarse con la API de Stripe
     */
    Session crearSesionCheckout(Long usuarioId, String tipoPlan) throws StripeException;

}

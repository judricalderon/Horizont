package co.edu.unbosque.horizont.service.client.stripe;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

public interface InterfaceStripeSubscriptionService {

    Session crearSesionCheckout(Long usuarioId, String tipoPlan) throws StripeException;

    void cancelarSuscripcion(String stripeSubscriptionId) throws StripeException;
}

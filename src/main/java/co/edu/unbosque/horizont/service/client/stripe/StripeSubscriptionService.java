package co.edu.unbosque.horizont.service.client.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeSubscriptionService implements InterfaceStripeSubscriptionService {

    @Value("${stripe.api.secret-key}")
    private String stripeSecretKey;

    @Value("${stripe.api.success-url}")
    private String successUrl;

    @Value("${stripe.api.cancel-url}")
    private String cancelUrl;
    @Value("${stripe.price.mensual}")
    private String mensualPriceId;

    @Value("${stripe.price.anual}")
    private String anualPriceId;

    @Override
    public Session crearSesionCheckout(Long usuarioId, String tipoPlan) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        String priceId = tipoPlan.equalsIgnoreCase("anual") ? anualPriceId : mensualPriceId;

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setSuccessUrl(successUrl)
                .setCancelUrl(cancelUrl)
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setPrice(priceId)
                                .setQuantity(1L)
                                .build()
                )
                .putMetadata("usuarioId", usuarioId.toString())
                .putMetadata("tipoPlan", tipoPlan)
                .build();

        return Session.create(params);
    }


    @Override
    public void cancelarSuscripcion(String stripeSubscriptionId) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        com.stripe.model.Subscription subscription =
                com.stripe.model.Subscription.retrieve(stripeSubscriptionId);
        subscription.cancel();
    }
}

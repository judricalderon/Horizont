package co.edu.unbosque.horizont.service.client.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio {@link InterfaceStripeSubscriptionService} que gestiona
 * la interacción con la API de Stripe para crear sesiones de suscripción.
 *
 * Utiliza las claves y configuraciones definidas en el archivo de propiedades de la aplicación.
 */

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

    /**
     * Crea una sesión de Stripe Checkout para una suscripción, basada en el tipo de plan
     * seleccionado por el usuario.
     *
     * @param usuarioId ID del usuario que desea iniciar la suscripción
     * @param tipoPlan tipo de plan (por ejemplo: "mensual" o "anual")
     * @return una instancia de {@link Session} que contiene la URL de redirección de pago
     * @throws StripeException si ocurre un error al comunicarse con la API de Stripe
     */

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


}

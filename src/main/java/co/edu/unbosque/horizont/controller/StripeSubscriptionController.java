package co.edu.unbosque.horizont.controller;

import co.edu.unbosque.horizont.service.client.stripe.InterfaceStripeSubscriptionService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para gestionar las suscripciones utilizando Stripe.
 *
 * Proporciona un punto de entrada para la creación de sesiones de pago
 * mediante Stripe Checkout. Esta clase se comunica con un servicio que encapsula
 * la lógica de negocio relacionada con las suscripciones.
 *
 * Ruta base: <code>/api/stripe/suscripcion</code>
 */

@RestController
@RequestMapping("/api/stripe/suscripcion")
public class StripeSubscriptionController {

    private final InterfaceStripeSubscriptionService stripeService;

    /**
     * Constructor que inyecta el servicio de Stripe utilizado para manejar las suscripciones.
     *
     * @param stripeService servicio que gestiona la lógica de creación de sesiones con Stripe
     */

    public StripeSubscriptionController(InterfaceStripeSubscriptionService stripeService) {
        this.stripeService = stripeService;
    }

    /**
     * Crea una sesión de pago con Stripe Checkout y devuelve la URL.
     * @param usuarioId ID del usuario autenticado o seleccionado
     * @param tipoPlan Tipo de plan: mensual, anual, etc.
     * @return URL a la que debe redirigirse el frontend para pagar
     */
    @PostMapping("/crear-sesion")
    public ResponseEntity<String> crearSesionCheckout(
            @RequestParam Long usuarioId,
            @RequestParam String tipoPlan) {
        try {
            Session session = stripeService.crearSesionCheckout(usuarioId, tipoPlan);
            return ResponseEntity.status(HttpStatus.CREATED).body(session.getUrl());
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear sesión de Stripe: " + e.getMessage());
        }
    }
}

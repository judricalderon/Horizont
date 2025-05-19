package co.edu.unbosque.horizont.controller;

import co.edu.unbosque.horizont.dto.internal.SuscripcionRequestDTO;
import co.edu.unbosque.horizont.service.internal.InterfaceSuscripcionService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.Subscription;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
/**
 * Controlador REST encargado de manejar los eventos enviados por Stripe a través de Webhooks.
 *
 * Este controlador escucha peticiones entrantes desde Stripe y procesa eventos como
 * la finalización exitosa de una sesión de pago (`checkout.session.completed`),
 * creando una suscripción interna en el sistema.
 *
 * Ruta base: <code>/api/stripe/webhook</code>
 */

@RestController
@RequestMapping("/api/stripe/webhook")
public class StripeWebhookController {

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    private final InterfaceSuscripcionService suscripcionService;

    /**
     * Constructor que inyecta el servicio de suscripciones utilizado para registrar
     * y gestionar la suscripción del usuario en el sistema interno.
     *
     * @param suscripcionService servicio interno que gestiona la lógica de suscripciones
     */

    public StripeWebhookController(InterfaceSuscripcionService suscripcionService) {
        this.suscripcionService = suscripcionService;
    }

    /**
     * Procesa el evento enviado por Stripe al endpoint de webhook. Valida la firma del evento
     * para asegurar su autenticidad, y si se trata de un evento <code>checkout.session.completed</code>,
     * registra una nueva suscripción en el sistema.
     *
     * @param payload cuerpo crudo del evento enviado por Stripe
     * @param sigHeader encabezado de la firma enviada por Stripe para verificar la autenticidad del evento
     * @return respuesta HTTP indicando si el evento fue procesado correctamente o si ocurrió algún error
     */

    @PostMapping
    public ResponseEntity<String> handleStripeWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {

        try {
            Event event = Webhook.constructEvent(payload, sigHeader, webhookSecret);


            if ("checkout.session.completed".equals(event.getType())) {
                // Intenta deserializar automáticamente
                Session session = (Session) event.getDataObjectDeserializer()
                        .getObject()
                        .orElse(null);
                System.out.println("✔ Webhook recibido y validado correctamente");

                // Si no hay sesión válida, la recuperamos manualmente
                if (session == null) {
                    String rawJson = event.getData().getObject().toJson();
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode node = mapper.readTree(rawJson);
                    String sessionId = node.get("id").asText();
                    session = Session.retrieve(sessionId);
                }

                // Recupera la suscripción desde Stripe
                String subscriptionId = session.getSubscription();
                Subscription stripeSubscription = Subscription.retrieve(subscriptionId);

                Long usuarioId = Long.parseLong(session.getMetadata().get("usuarioId"));
                String tipoPlan = session.getMetadata().get("tipoPlan");

                SuscripcionRequestDTO dto = new SuscripcionRequestDTO();
                dto.setUsuarioId(usuarioId);
                dto.setTipoPlan(tipoPlan);
                dto.setActivo(true);
                dto.setStripeCustomerId(session.getCustomer());
                dto.setStripeSubscriptionId(subscriptionId);

                // Convertir fechas desde Stripe
                LocalDate inicio = Instant.ofEpochSecond(stripeSubscription.getStartDate())
                        .atZone(ZoneOffset.UTC).toLocalDate();
                dto.setFechaInicio(inicio);

                if (stripeSubscription.getCancelAt() != null) {
                    LocalDate cancelacion = Instant.ofEpochSecond(stripeSubscription.getCancelAt())
                            .atZone(ZoneOffset.UTC).toLocalDate();
                    dto.setFechaCancelacion(cancelacion);
                }
                System.out.println("Creando suscripción para usuario ID: " + usuarioId);
                suscripcionService.crearSuscripcion(dto);
            }

            return ResponseEntity.ok("Evento procesado");

        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(400).body("Firma no válida: " + e.getMessage());
        } catch (StripeException e) {
            return ResponseEntity.status(500).body("Error de Stripe: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error inesperado: " + e.getMessage());
        }
    }
}

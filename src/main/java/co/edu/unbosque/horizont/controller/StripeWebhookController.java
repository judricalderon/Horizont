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

@RestController
@RequestMapping("/api/stripe/webhook")
public class StripeWebhookController {

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    private final InterfaceSuscripcionService suscripcionService;

    public StripeWebhookController(InterfaceSuscripcionService suscripcionService) {
        this.suscripcionService = suscripcionService;
    }

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

package co.edu.unbosque.horizont.dto.internal;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) utilizado para transportar la información necesaria
 * para crear o modificar una suscripción dentro del sistema.
 *
 * Contiene detalles como el ID del usuario, el tipo de plan, fechas relevantes,
 * estado de la suscripción y referencias a identificadores de Stripe.
 */

public class SuscripcionRequestDTO {

    private Long usuarioId;
    private String tipoPlan;
    private LocalDate fechaInicio;
    private LocalDate fechaCancelacion;
    private boolean activo;
    private String stripeCustomerId;
    private String stripeSubscriptionId;


    /**
     * Obtiene el ID del usuario asociado a la suscripción.
     *
     * @return ID del usuario
     */
    public Long getUsuarioId() {
        return usuarioId;
    }

    /**
     * Establece el ID del usuario asociado a la suscripción.
     *
     * @param usuarioId ID del usuario
     */
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    /**
     * Obtiene el tipo de plan de la suscripción (mensual, anual, etc.).
     *
     * @return tipo de plan
     */
    public String getTipoPlan() {
        return tipoPlan;
    }

    /**
     * Establece el tipo de plan de la suscripción.
     *
     * @param tipoPlan tipo de plan (por ejemplo: "mensual", "anual")
     */
    public void setTipoPlan(String tipoPlan) {
        this.tipoPlan = tipoPlan;
    }

    /**
     * Obtiene la fecha de inicio de la suscripción.
     *
     * @return fecha de inicio
     */
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Establece la fecha de inicio de la suscripción.
     *
     * @param fechaInicio fecha en que comienza la suscripción
     */
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Obtiene la fecha de cancelación programada de la suscripción, si existe.
     *
     * @return fecha de cancelación o {@code null} si no aplica
     */
    public LocalDate getFechaCancelacion() {
        return fechaCancelacion;
    }

    /**
     * Establece la fecha de cancelación de la suscripción.
     *
     * @param fechaCancelacion fecha en que se cancelará la suscripción
     */
    public void setFechaCancelacion(LocalDate fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    /**
     * Indica si la suscripción está activa.
     *
     * @return {@code true} si está activa, {@code false} si no
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * Establece si la suscripción está activa.
     *
     * @param activo estado de la suscripción
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * Obtiene el ID del cliente en Stripe asociado a esta suscripción.
     *
     * @return ID de cliente en Stripe
     */
    public String getStripeCustomerId() {
        return stripeCustomerId;
    }

    /**
     * Establece el ID del cliente en Stripe.
     *
     * @param stripeCustomerId ID del cliente en Stripe
     */
    public void setStripeCustomerId(String stripeCustomerId) {
        this.stripeCustomerId = stripeCustomerId;
    }

    /**
     * Obtiene el ID de la suscripción en Stripe.
     *
     * @return ID de la suscripción en Stripe
     */
    public String getStripeSubscriptionId() {
        return stripeSubscriptionId;
    }

    /**
     * Establece el ID de la suscripción en Stripe.
     *
     * @param stripeSubscriptionId ID de la suscripción en Stripe
     */
    public void setStripeSubscriptionId(String stripeSubscriptionId) {
        this.stripeSubscriptionId = stripeSubscriptionId;
    }
}
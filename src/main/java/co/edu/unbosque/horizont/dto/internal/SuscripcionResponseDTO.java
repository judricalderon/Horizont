package co.edu.unbosque.horizont.dto.internal;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) utilizado para representar la información de una suscripción
 * que ha sido creada o recuperada desde el sistema.
 *
 * Contiene datos internos como el ID de la suscripción, información del usuario,
 * tipo de plan, fechas de inicio y cancelación, estado, y detalles vinculados a Stripe.
 */

public class SuscripcionResponseDTO {

    private Long id;
    private Long usuarioId;
    private String tipoPlan;
    private LocalDate fechaInicio;
    private LocalDate fechaCancelacion;
    private boolean activo;
    private String stripeCustomerId;
    private String stripeSubscriptionId;

    /**
     * Obtiene el ID interno de la suscripción.
     *
     * @return ID único de la suscripción
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID interno de la suscripción.
     *
     * @param id ID único de la suscripción
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el ID del usuario asociado a esta suscripción.
     *
     * @return ID del usuario
     */
    public Long getUsuarioId() {
        return usuarioId;
    }

    /**
     * Establece el ID del usuario asociado a esta suscripción.
     *
     * @param usuarioId ID del usuario
     */
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    /**
     * Obtiene el tipo de plan de la suscripción (ej. mensual, anual).
     *
     * @return tipo de plan
     */
    public String getTipoPlan() {
        return tipoPlan;
    }

    /**
     * Establece el tipo de plan de la suscripción.
     *
     * @param tipoPlan tipo de plan (ej. "mensual", "anual")
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
     * Obtiene la fecha de cancelación programada de la suscripción, si aplica.
     *
     * @return fecha de cancelación o {@code null} si no está programada
     */
    public LocalDate getFechaCancelacion() {
        return fechaCancelacion;
    }

    /**
     * Establece la fecha de cancelación de la suscripción.
     *
     * @param fechaCancelacion fecha programada para la cancelación
     */
    public void setFechaCancelacion(LocalDate fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    /**
     * Indica si la suscripción está activa.
     *
     * @return {@code true} si está activa, {@code false} si está cancelada o inactiva
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * Establece si la suscripción está activa.
     *
     * @param activo {@code true} si está activa, {@code false} en caso contrario
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * Obtiene el ID del cliente en Stripe asociado a esta suscripción.
     *
     * @return ID del cliente en Stripe
     */
    public String getStripeCustomerId() {
        return stripeCustomerId;
    }

    /**
     * Establece el ID del cliente en Stripe asociado a esta suscripción.
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
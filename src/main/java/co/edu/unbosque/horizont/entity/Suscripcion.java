package co.edu.unbosque.horizont.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entidad que representa una suscripción activa o pasada de un usuario dentro del sistema.
 *
 * Esta clase está mapeada a la tabla <code>suscripciones</code> en la base de datos.
 * Almacena información relacionada con el estado de la suscripción, fechas, plan,
 * y detalles de integración con Stripe.
 */

@Entity
@Table(name = "suscripciones")
public class Suscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean activo;

    @Column(name = "fecha_cancelacion")
    private LocalDate fechaCancelacion;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "stripe_customer_id")
    private String stripeCustomerId;

    @Column(name = "stripe_subscription_id")
    private String stripeSubscriptionId;

    @Column(name = "tipo_plan")
    private String tipoPlan;

    @Column(name = "usuario_id")
    private Long usuarioId;

    // Getters y setters
    /**
     * Obtiene el ID único de la suscripción en la base de datos.
     *
     * @return ID de la suscripción
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID único de la suscripción.
     *
     * @param id ID de la suscripción
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el ID del usuario al que pertenece esta suscripción.
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
     * Obtiene el tipo de plan (ej. mensual, anual) asociado a la suscripción.
     *
     * @return tipo de plan
     */
    public String getTipoPlan() {
        return tipoPlan;
    }

    /**
     * Establece el tipo de plan asociado a la suscripción.
     *
     * @param tipoPlan tipo de plan
     */
    public void setTipoPlan(String tipoPlan) {
        this.tipoPlan = tipoPlan;
    }

    /**
     * Obtiene la fecha en que comenzó la suscripción.
     *
     * @return fecha de inicio
     */
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Establece la fecha en que comenzó la suscripción.
     *
     * @param fechaInicio fecha de inicio
     */
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Obtiene la fecha programada de cancelación de la suscripción, si existe.
     *
     * @return fecha de cancelación o {@code null} si no está definida
     */
    public LocalDate getFechaCancelacion() {
        return fechaCancelacion;
    }

    /**
     * Establece la fecha programada de cancelación de la suscripción.
     *
     * @param fechaCancelacion fecha de cancelación
     */
    public void setFechaCancelacion(LocalDate fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    /**
     * Indica si la suscripción está actualmente activa.
     *
     * @return {@code true} si está activa, {@code false} si está cancelada o expirada
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * Establece el estado de actividad de la suscripción.
     *
     * @param activo {@code true} si la suscripción está activa
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * Obtiene el ID del cliente en Stripe relacionado con esta suscripción.
     *
     * @return ID del cliente en Stripe
     */
    public String getStripeCustomerId() {
        return stripeCustomerId;
    }

    /**
     * Establece el ID del cliente en Stripe relacionado con esta suscripción.
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
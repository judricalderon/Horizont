package co.edu.unbosque.horizont.dto.client.alpaca;

/**
 * DTO (Data Transfer Object) que representa las divulgaciones legales requeridas
 * por la plataforma Alpaca como parte del proceso de verificación de identidad.
 *
 * Incluye indicadores sobre el estatus regulatorio y vínculos políticos del usuario
 * o su familia inmediata.
 */
public class DisclosuresDTO {

    /**
     * Indica si el usuario es una persona con control sobre una empresa pública (control person).
     */
    private boolean is_control_person;

    /**
     * Indica si el usuario está afiliado a una bolsa de valores o a FINRA.
     */
    private boolean is_affiliated_exchange_or_finra;

    /**
     * Indica si el usuario es una persona políticamente expuesta (PEP).
     */
    private boolean is_politically_exposed;

    /**
     * Indica si un familiar inmediato del usuario es una persona políticamente expuesta.
     */
    private boolean immediate_family_exposed;

    /**
     * Obtiene si el usuario es una persona con control sobre una empresa pública.
     *
     * @return {@code true} si es una persona con control, {@code false} si no
     */
    public boolean isIs_control_person() {
        return is_control_person;
    }

    /**
     * Establece si el usuario es una persona con control sobre una empresa pública.
     *
     * @param is_control_person valor booleano que indica si lo es
     */
    public void setIs_control_person(boolean is_control_person) {
        this.is_control_person = is_control_person;
    }

    /**
     * Obtiene si el usuario está afiliado a una bolsa de valores o a FINRA.
     *
     * @return {@code true} si está afiliado, {@code false} si no
     */
    public boolean isIs_affiliated_exchange_or_finra() {
        return is_affiliated_exchange_or_finra;
    }

    /**
     * Establece si el usuario está afiliado a una bolsa de valores o a FINRA.
     *
     * @param is_affiliated_exchange_or_finra valor booleano que indica si lo está
     */
    public void setIs_affiliated_exchange_or_finra(boolean is_affiliated_exchange_or_finra) {
        this.is_affiliated_exchange_or_finra = is_affiliated_exchange_or_finra;
    }

    /**
     * Obtiene si el usuario es una persona políticamente expuesta.
     *
     * @return {@code true} si lo es, {@code false} si no
     */
    public boolean isIs_politically_exposed() {
        return is_politically_exposed;
    }

    /**
     * Establece si el usuario es una persona políticamente expuesta.
     *
     * @param is_politically_exposed valor booleano que indica si lo es
     */
    public void setIs_politically_exposed(boolean is_politically_exposed) {
        this.is_politically_exposed = is_politically_exposed;
    }

    /**
     * Obtiene si un familiar inmediato del usuario es políticamente expuesto.
     *
     * @return {@code true} si lo es, {@code false} si no
     */
    public boolean isImmediate_family_exposed() {
        return immediate_family_exposed;
    }

    /**
     * Establece si un familiar inmediato del usuario es políticamente expuesto.
     *
     * @param immediate_family_exposed valor booleano que indica si lo es
     */
    public void setImmediate_family_exposed(boolean immediate_family_exposed) {
        this.immediate_family_exposed = immediate_family_exposed;
    }
}

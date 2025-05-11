package co.edu.unbosque.horizont.dto.client.alpaca;

/**
 * DTO (Data Transfer Object) que representa un acuerdo firmado por el usuario
 * para propósitos de integración con el servicio Alpaca.
 *
 * Contiene información sobre el tipo de acuerdo, la fecha de firma y la IP del usuario.
 */
public class AgreementDTO {

    /**
     * Tipo de acuerdo firmado, por ejemplo: "account_agreement", "margin_agreement".
     */
    private String agreement;

    /**
     * Fecha y hora en que el acuerdo fue firmado, en formato ISO 8601, por ejemplo: "2025-05-07T12:00:00Z".
     */
    private String signed_at;

    /**
     * Dirección IP desde la cual el usuario firmó el acuerdo.
     */
    private String ip_address;

    /**
     * Obtiene el tipo de acuerdo.
     *
     * @return tipo de acuerdo firmado
     */
    public String getAgreement() {
        return agreement;
    }

    /**
     * Establece el tipo de acuerdo.
     *
     * @param agreement tipo de acuerdo firmado
     */
    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    /**
     * Obtiene la fecha y hora en la que se firmó el acuerdo.
     *
     * @return fecha y hora de la firma en formato ISO 8601
     */
    public String getSigned_at() {
        return signed_at;
    }

    /**
     * Establece la fecha y hora de firma del acuerdo.
     *
     * @param signed_at fecha y hora de la firma en formato ISO 8601
     */
    public void setSigned_at(String signed_at) {
        this.signed_at = signed_at;
    }

    /**
     * Obtiene la dirección IP del usuario que firmó el acuerdo.
     *
     * @return dirección IP del firmante
     */
    public String getIp_address() {
        return ip_address;
    }

    /**
     * Establece la dirección IP del usuario que firmó el acuerdo.
     *
     * @param ip_address dirección IP del firmante
     */
    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }
}

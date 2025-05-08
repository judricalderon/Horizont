package co.edu.unbosque.horizont.dto.client.alpaca;


public class AgreementDTO {
    private String agreement;     // Ej: "account_agreement", "margin_agreement"
    private String signed_at;     // Formato ISO 8601: "2025-05-07T12:00:00Z"
    private String ip_address;    // IP del usuario que acepta

    public String getAgreement() {
        return agreement;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    public String getSigned_at() {
        return signed_at;
    }

    public void setSigned_at(String signed_at) {
        this.signed_at = signed_at;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }
}
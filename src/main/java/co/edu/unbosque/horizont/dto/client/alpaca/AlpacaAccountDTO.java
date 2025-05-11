package co.edu.unbosque.horizont.dto.client.alpaca;

import java.util.List;

/**
 * DTO (Data Transfer Object) que representa una cuenta de usuario para la plataforma Alpaca.
 *
 * Contiene información de contacto, identidad, divulgaciones legales y acuerdos firmados
 * necesarios para la creación de una cuenta en Alpaca.
 */
public class AlpacaAccountDTO {

    /**
     * Información de contacto del usuario.
     */
    private ContactDTO contact;

    /**
     * Información de identidad del usuario (por ejemplo, nombre, número de identificación).
     */
    private IdentityDTO identity;

    /**
     * Información sobre las divulgaciones legales proporcionadas por el usuario.
     */
    private DisclosuresDTO disclosures;

    /**
     * Lista de acuerdos firmados por el usuario, requeridos por Alpaca.
     */
    private List<AgreementDTO> agreements;

    /**
     * Obtiene la información de contacto del usuario.
     *
     * @return un objeto {@link ContactDTO} con los datos de contacto
     */
    public ContactDTO getContact() {
        return contact;
    }

    /**
     * Establece la información de contacto del usuario.
     *
     * @param contact un objeto {@link ContactDTO} con los datos de contacto
     */
    public void setContact(ContactDTO contact) {
        this.contact = contact;
    }

    /**
     * Obtiene la información de identidad del usuario.
     *
     * @return un objeto {@link IdentityDTO} con los datos de identidad
     */
    public IdentityDTO getIdentity() {
        return identity;
    }

    /**
     * Establece la información de identidad del usuario.
     *
     * @param identity un objeto {@link IdentityDTO} con los datos de identidad
     */
    public void setIdentity(IdentityDTO identity) {
        this.identity = identity;
    }

    /**
     * Obtiene las divulgaciones legales proporcionadas por el usuario.
     *
     * @return un objeto {@link DisclosuresDTO} con los datos de divulgaciones
     */
    public DisclosuresDTO getDisclosures() {
        return disclosures;
    }

    /**
     * Establece las divulgaciones legales del usuario.
     *
     * @param disclosures un objeto {@link DisclosuresDTO} con los datos de divulgaciones
     */
    public void setDisclosures(DisclosuresDTO disclosures) {
        this.disclosures = disclosures;
    }

    /**
     * Obtiene la lista de acuerdos firmados por el usuario.
     *
     * @return una lista de objetos {@link AgreementDTO} representando los acuerdos firmados
     */
    public List<AgreementDTO> getAgreements() {
        return agreements;
    }

    /**
     * Establece la lista de acuerdos firmados por el usuario.
     *
     * @param agreements una lista de objetos {@link AgreementDTO}
     */
    public void setAgreements(List<AgreementDTO> agreements) {
        this.agreements = agreements;
    }
}

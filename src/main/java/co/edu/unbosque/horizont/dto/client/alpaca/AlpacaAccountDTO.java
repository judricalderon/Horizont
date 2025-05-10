package co.edu.unbosque.horizont.dto.client.alpaca;


import java.util.List;

public class AlpacaAccountDTO {

    private ContactDTO contact;
    private IdentityDTO identity;
    private DisclosuresDTO disclosures;
    private List<AgreementDTO> agreements;



    public ContactDTO getContact() {
        return contact;
    }

    public void setContact(ContactDTO contact) {
        this.contact = contact;
    }

    public IdentityDTO getIdentity() {
        return identity;
    }

    public void setIdentity(IdentityDTO identity) {
        this.identity = identity;
    }

    public DisclosuresDTO getDisclosures() {
        return disclosures;
    }

    public void setDisclosures(DisclosuresDTO disclosures) {
        this.disclosures = disclosures;
    }

    public List<AgreementDTO> getAgreements() {
        return agreements;
    }

    public void setAgreements(List<AgreementDTO> agreements) {
        this.agreements = agreements;
    }
}
package co.edu.unbosque.horizont.dto.client.alpaca;

import java.util.List;

public class IdentityDTO {
    private String given_name;
    private String family_name;
    private String date_of_birth;
    private String tax_id;
    private String tax_id_type = "US_SSN";
    private String country_of_citizenship = "USA";
    private String country_of_birth = "USA";
    private String country_of_tax_residence = "USA";
    private List<String> funding_source;

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getTax_id() {
        return tax_id;
    }

    public void setTax_id(String tax_id) {
        this.tax_id = tax_id;
    }

    public String getTax_id_type() {
        return tax_id_type;
    }

    public void setTax_id_type(String tax_id_type) {
        this.tax_id_type = tax_id_type;
    }

    public String getCountry_of_citizenship() {
        return country_of_citizenship;
    }

    public void setCountry_of_citizenship(String country_of_citizenship) {
        this.country_of_citizenship = country_of_citizenship;
    }

    public String getCountry_of_birth() {
        return country_of_birth;
    }

    public void setCountry_of_birth(String country_of_birth) {
        this.country_of_birth = country_of_birth;
    }

    public String getCountry_of_tax_residence() {
        return country_of_tax_residence;
    }

    public void setCountry_of_tax_residence(String country_of_tax_residence) {
        this.country_of_tax_residence = country_of_tax_residence;
    }

    public List<String> getFunding_source() {
        return funding_source;
    }

    public void setFunding_source(List<String> funding_source) {
        this.funding_source = funding_source;
    }
}
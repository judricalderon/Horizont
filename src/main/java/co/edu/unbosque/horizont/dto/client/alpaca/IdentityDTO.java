package co.edu.unbosque.horizont.dto.client.alpaca;

import java.util.List;

/**
 * DTO (Data Transfer Object) que representa la información de identidad personal
 * de un usuario, utilizada en el proceso de verificación de cuentas con la plataforma Alpaca.
 *
 * Incluye nombres, fecha de nacimiento, información tributaria y país de residencia.
 */
public class IdentityDTO {

    /**
     * Nombre(s) del usuario.
     */
    private String given_name;

    /**
     * Apellido(s) del usuario.
     */
    private String family_name;

    /**
     * Fecha de nacimiento del usuario en formato ISO 8601 (por ejemplo, "1990-01-01").
     */
    private String date_of_birth;

    /**
     * Número de identificación tributaria del usuario (por ejemplo, SSN en EE. UU.).
     */
    private String tax_id;

    /**
     * Tipo de identificación tributaria. Valor por defecto: "US_SSN".
     */
    private String tax_id_type = "US_SSN";

    /**
     * País de ciudadanía del usuario. Valor por defecto: "USA".
     */
    private String country_of_citizenship = "USA";

    /**
     * País de nacimiento del usuario. Valor por defecto: "USA".
     */
    private String country_of_birth = "USA";

    /**
     * País de residencia fiscal del usuario. Valor por defecto: "USA".
     */
    private String country_of_tax_residence = "USA";

    /**
     * Lista de fuentes de fondos declaradas por el usuario (por ejemplo: "salary", "inheritance").
     */
    private List<String> funding_source;

    /**
     * Obtiene el nombre del usuario.
     *
     * @return nombre(s) del usuario
     */
    public String getGiven_name() {
        return given_name;
    }

    /**
     * Establece el nombre del usuario.
     *
     * @param given_name nombre(s) del usuario
     */
    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    /**
     * Obtiene el apellido del usuario.
     *
     * @return apellido(s) del usuario
     */
    public String getFamily_name() {
        return family_name;
    }

    /**
     * Establece el apellido del usuario.
     *
     * @param family_name apellido(s) del usuario
     */
    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    /**
     * Obtiene la fecha de nacimiento del usuario.
     *
     * @return fecha de nacimiento en formato ISO 8601
     */
    public String getDate_of_birth() {
        return date_of_birth;
    }

    /**
     * Establece la fecha de nacimiento del usuario.
     *
     * @param date_of_birth fecha de nacimiento en formato ISO 8601
     */
    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    /**
     * Obtiene el número de identificación tributaria del usuario.
     *
     * @return número de identificación fiscal
     */
    public String getTax_id() {
        return tax_id;
    }

    /**
     * Establece el número de identificación tributaria del usuario.
     *
     * @param tax_id número de identificación fiscal
     */
    public void setTax_id(String tax_id) {
        this.tax_id = tax_id;
    }

    /**
     * Obtiene el tipo de identificación tributaria del usuario.
     *
     * @return tipo de ID tributaria (por ejemplo: "US_SSN")
     */
    public String getTax_id_type() {
        return tax_id_type;
    }

    /**
     * Establece el tipo de identificación tributaria del usuario.
     *
     * @param tax_id_type tipo de ID tributaria
     */
    public void setTax_id_type(String tax_id_type) {
        this.tax_id_type = tax_id_type;
    }

    /**
     * Obtiene el país de ciudadanía del usuario.
     *
     * @return país de ciudadanía
     */
    public String getCountry_of_citizenship() {
        return country_of_citizenship;
    }

    /**
     * Establece el país de ciudadanía del usuario.
     *
     * @param country_of_citizenship país de ciudadanía
     */
    public void setCountry_of_citizenship(String country_of_citizenship) {
        this.country_of_citizenship = country_of_citizenship;
    }

    /**
     * Obtiene el país de nacimiento del usuario.
     *
     * @return país de nacimiento
     */
    public String getCountry_of_birth() {
        return country_of_birth;
    }

    /**
     * Establece el país de nacimiento del usuario.
     *
     * @param country_of_birth país de nacimiento
     */
    public void setCountry_of_birth(String country_of_birth) {
        this.country_of_birth = country_of_birth;
    }

    /**
     * Obtiene el país de residencia fiscal del usuario.
     *
     * @return país de residencia fiscal
     */
    public String getCountry_of_tax_residence() {
        return country_of_tax_residence;
    }

    /**
     * Establece el país de residencia fiscal del usuario.
     *
     * @param country_of_tax_residence país de residencia fiscal
     */
    public void setCountry_of_tax_residence(String country_of_tax_residence) {
        this.country_of_tax_residence = country_of_tax_residence;
    }

    /**
     * Obtiene las fuentes de fondos declaradas por el usuario.
     *
     * @return lista de fuentes de ingreso o financiamiento
     */
    public List<String> getFunding_source() {
        return funding_source;
    }

    /**
     * Establece las fuentes de fondos declaradas por el usuario.
     *
     * @param funding_source lista de fuentes de ingreso o financiamiento
     */
    public void setFunding_source(List<String> funding_source) {
        this.funding_source = funding_source;
    }
}

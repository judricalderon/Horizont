package co.edu.unbosque.horizont.dto.client.alpaca;

import java.util.List;

/**
 * DTO (Data Transfer Object) que representa la información de contacto de un usuario
 * en el proceso de registro de cuenta en la plataforma Alpaca.
 *
 * Contiene detalles como dirección de correo electrónico, número de teléfono,
 * dirección física y país.
 */
public class ContactDTO {

    /**
     * Dirección de correo electrónico del usuario.
     */
    private String email_address;

    /**
     * Número de teléfono del usuario.
     */
    private String phone_number;

    /**
     * Dirección física del usuario, dividida en líneas (por ejemplo: calle, apartamento).
     */
    private List<String> street_address;

    /**
     * Ciudad de residencia del usuario.
     */
    private String city;

    /**
     * Estado o provincia de residencia del usuario.
     */
    private String state;

    /**
     * Código postal correspondiente a la dirección del usuario.
     */
    private String postal_code;

    /**
     * País de residencia del usuario.
     */
    private String country;

    /**
     * Obtiene la dirección de correo electrónico del usuario.
     *
     * @return email del usuario
     */
    public String getEmail_address() {
        return email_address;
    }

    /**
     * Establece la dirección de correo electrónico del usuario.
     *
     * @param email_address email del usuario
     */
    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    /**
     * Obtiene el número de teléfono del usuario.
     *
     * @return número de teléfono
     */
    public String getPhone_number() {
        return phone_number;
    }

    /**
     * Establece el número de teléfono del usuario.
     *
     * @param phone_number número de teléfono
     */
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    /**
     * Obtiene la dirección física del usuario en forma de lista de líneas.
     *
     * @return lista de líneas de dirección
     */
    public List<String> getStreet_address() {
        return street_address;
    }

    /**
     * Establece la dirección física del usuario.
     *
     * @param street_address lista de líneas de dirección
     */
    public void setStreet_address(List<String> street_address) {
        this.street_address = street_address;
    }

    /**
     * Obtiene la ciudad de residencia del usuario.
     *
     * @return ciudad del usuario
     */
    public String getCity() {
        return city;
    }

    /**
     * Establece la ciudad de residencia del usuario.
     *
     * @param city ciudad del usuario
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Obtiene el estado o provincia del usuario.
     *
     * @return estado o provincia
     */
    public String getState() {
        return state;
    }

    /**
     * Establece el estado o provincia del usuario.
     *
     * @param state estado o provincia
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Obtiene el código postal del usuario.
     *
     * @return código postal
     */
    public String getPostal_code() {
        return postal_code;
    }

    /**
     * Establece el código postal del usuario.
     *
     * @param postal_code código postal
     */
    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    /**
     * Obtiene el país de residencia del usuario.
     *
     * @return país del usuario
     */
    public String getCountry() {
        return country;
    }

    /**
     * Establece el país de residencia del usuario.
     *
     * @param country país del usuario
     */
    public void setCountry(String country) {
        this.country = country;
    }
}

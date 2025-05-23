package co.edu.unbosque.horizont.dto.internal;

import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 * DTO (Data Transfer Object) que representa la información básica y extendida
 * de un usuario dentro del sistema.
 *
 * Este objeto se utiliza principalmente para transferir datos entre capas
 * (controladores, servicios, etc.) sin exponer directamente la entidad persistente.
 */

public class UsuarioDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String direccion;
    private String ciudad;
    private String estado;
    private String codigoPostal;
    private String pais;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;
    private String ssn;
    private String codigoVerificacion;
    private LocalDateTime expiracionCodigo;
    private boolean verificado;
    private String password;
    private boolean esPremium;
    private String loginCodigoVerificacion;
    private LocalDateTime loginExpiracionCodigo;
    private boolean loginVerificado;
    private boolean isAdmin = false;
    private double balanceUsd = 0.0;
    /**
     * Constructor vacío para frameworks que requieren instanciación por reflexión.
     */

    public UsuarioDTO() {}
    /**
     * Constructor completo para inicializar todos los campos del usuario.
     *
     * @param id ID del usuario
     * @param nombre nombre del usuario
     * @param apellido apellido del usuario
     * @param correo correo electrónico del usuario
     * @param telefono número de teléfono
     * @param direccion dirección física
     * @param ciudad ciudad del usuario
     * @param estado estado o provincia
     * @param codigoPostal código postal
     * @param pais país de residencia
     * @param fechaNacimiento fecha de nacimiento
     * @param ssn número de seguro social u otro identificador
     * @param codigoVerificacion código de verificación enviado al usuario
     * @param expiracionCodigo fecha y hora de expiración del código
     * @param verificado indica si el usuario ha sido verificado
     * @param password contraseña del usuario
     * @param esPremium indica si el usuario tiene una suscripción premium
     * @param loginCodigoVerificacion código de verificación usado para login
     * @param loginExpiracionCodigo fecha y hora de expiración del código de login
     * @param loginVerificado indica si el login ha sido verificado
     * @param isAdmin  Indica si el usuario es admin
     * @param balanceUsd Saldo del usuario
     */

    public UsuarioDTO(Long id, String nombre, String apellido, String correo, String telefono, String direccion,
                      String ciudad, String estado, String codigoPostal, String pais,
                      LocalDate fechaNacimiento, String ssn, String codigoVerificacion,
                      LocalDateTime expiracionCodigo, boolean verificado,
                      String loginCodigoVerificacion, LocalDateTime loginExpiracionCodigo, boolean loginVerificado,
                      String password, boolean esPremium, boolean isAdmin, double balanceUsd) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.estado = estado;
        this.codigoPostal = codigoPostal;
        this.pais = pais;
        this.fechaNacimiento = fechaNacimiento;
        this.ssn = ssn;
        this.codigoVerificacion = codigoVerificacion;
        this.expiracionCodigo = expiracionCodigo;
        this.verificado = verificado;
        this.loginCodigoVerificacion = loginCodigoVerificacion;
        this.loginExpiracionCodigo = loginExpiracionCodigo;
        this.loginVerificado = loginVerificado;
        this.password = password;
        this.esPremium = esPremium;
        this.isAdmin = isAdmin;
        this.balanceUsd = balanceUsd;
    }

    /** @return ID del usuario */
    public Long getId() { return id; }

    /** @param id ID del usuario */
    public void setId(Long id) { this.id = id; }

    /** @return nombre del usuario */
    public String getNombre() { return nombre; }

    /** @param nombre nombre del usuario */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /** @return apellido del usuario */
    public String getApellido() { return apellido; }

    /** @param apellido apellido del usuario */
    public void setApellido(String apellido) { this.apellido = apellido; }

    /** @return correo electrónico del usuario */
    public String getCorreo() { return correo; }

    /** @param correo correo electrónico del usuario */
    public void setCorreo(String correo) { this.correo = correo; }

    /** @return número de teléfono */
    public String getTelefono() { return telefono; }

    /** @param telefono número de teléfono */
    public void setTelefono(String telefono) { this.telefono = telefono; }

    /** @return dirección del usuario */
    public String getDireccion() { return direccion; }

    /** @param direccion dirección del usuario */
    public void setDireccion(String direccion) { this.direccion = direccion; }

    /** @return ciudad del usuario */
    public String getCiudad() { return ciudad; }

    /** @param ciudad ciudad del usuario */
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    /** @return estado o provincia del usuario */
    public String getEstado() { return estado; }

    /** @param estado estado o provincia del usuario */
    public void setEstado(String estado) { this.estado = estado; }

    /** @return código postal del usuario */
    public String getCodigoPostal() { return codigoPostal; }

    /** @param codigoPostal código postal del usuario */
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }

    /** @return país de residencia del usuario */
    public String getPais() { return pais; }

    /** @param pais país de residencia del usuario */
    public void setPais(String pais) { this.pais = pais; }

    /** @return fecha de nacimiento del usuario */
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }

    /** @param fechaNacimiento fecha de nacimiento del usuario */
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    /** @return número de seguro social (SSN) o identificador similar */
    public String getSsn() { return ssn; }

    /** @param ssn número de seguro social (SSN) o identificador similar */
    public void setSsn(String ssn) { this.ssn = ssn; }

    /** @return código de verificación enviado al usuario */
    public String getCodigoVerificacion() { return codigoVerificacion; }

    /** @param codigoVerificacion código de verificación enviado al usuario */
    public void setCodigoVerificacion(String codigoVerificacion) { this.codigoVerificacion = codigoVerificacion; }

    /** @return fecha y hora en que expira el código de verificación */
    public LocalDateTime getExpiracionCodigo() { return expiracionCodigo; }

    /** @param expiracionCodigo fecha y hora en que expira el código de verificación */
    public void setExpiracionCodigo(LocalDateTime expiracionCodigo) { this.expiracionCodigo = expiracionCodigo; }

    /** @return {@code true} si el usuario está verificado */
    public boolean isVerificado() { return verificado; }

    /** @param verificado {@code true} si el usuario está verificado */
    public void setVerificado(boolean verificado) { this.verificado = verificado; }

    /** @return contraseña del usuario */
    public String getPassword() { return password; }

    /** @param password contraseña del usuario */
    public void setPassword(String password) { this.password = password; }

    /** @return {@code true} si el usuario tiene suscripción premium */
    public boolean isEsPremium() { return esPremium; }

    /** @param esPremium {@code true} si el usuario tiene suscripción premium */
    public void setEsPremium(boolean esPremium) { this.esPremium = esPremium; }

    public String getLoginCodigoVerificacion() {
        return loginCodigoVerificacion;
    }

    public void setLoginCodigoVerificacion(String loginCodigoVerificacion) {
        this.loginCodigoVerificacion = loginCodigoVerificacion;
    }

    public LocalDateTime getLoginExpiracionCodigo() {
        return loginExpiracionCodigo;
    }

    public void setLoginExpiracionCodigo(LocalDateTime loginExpiracionCodigo) {
        this.loginExpiracionCodigo = loginExpiracionCodigo;
    }

    public boolean isLoginVerificado() {
        return loginVerificado;
    }

    public void setLoginVerificado(boolean loginVerificado) {
        this.loginVerificado = loginVerificado;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public double getBalanceUsd() {
        return balanceUsd;
    }

    public void setBalanceUsd(double balanceUsd) {
        this.balanceUsd = balanceUsd;
    }
}
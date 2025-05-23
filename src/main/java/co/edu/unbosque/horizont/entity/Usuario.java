package co.edu.unbosque.horizont.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad JPA que representa un usuario del sistema.
 *
 * Esta clase se almacena en la base de datos y contiene información personal,
 * de autenticación y de estado de verificación del usuario.
 */
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    private LocalDate fechaNacimiento;
    private String ssn;

    private String codigoVerificacion;
    private LocalDateTime expiracionCodigo;
    private boolean verificado = false;

    // Campos para verificación de login
    private String loginCodigoVerificacion;
    private LocalDateTime loginExpiracionCodigo;
    private boolean loginVerificado = false;

    private String password;
    private boolean esPremium = false;
    private boolean isAdmin = false;
    private double balanceUsd = 0.0;


    /**
     * Constructor vacío requerido por JPA.
     */
    public Usuario() {}

    /**
     * Constructor parcial que inicializa los campos principales del usuario.
     *
     * @param nombre nombre del usuario
     * @param apellido apellido del usuario
     * @param correo correo electrónico del usuario
     * @param telefono número de teléfono
     * @param direccion dirección física
     * @param ciudad ciudad de residencia
     * @param estado estado o provincia
     * @param codigoPostal código postal
     * @param pais país de residencia
     * @param fechaNacimiento fecha de nacimiento
     * @param ssn número de seguro social o identificador legal
     * @param codigoVerificacion código enviado para verificar la cuenta
     * @param isAdmin
     * @param balanceUsd Saldo del usuario
     */
    public Usuario(String nombre, String apellido, String correo, String telefono, String direccion,
                   String ciudad, String estado, String codigoPostal, String pais,
                   LocalDate fechaNacimiento, String ssn, String codigoVerificacion, boolean isAdmin, double balanceUsd) {
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
        this.verificado = false;
        this.expiracionCodigo = null;
        this.loginCodigoVerificacion = null;
        this.loginExpiracionCodigo = null;
        this.loginVerificado = false;
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

    /** @return número de teléfono del usuario */
    public String getTelefono() { return telefono; }

    /** @param telefono número de teléfono del usuario */
    public void setTelefono(String telefono) { this.telefono = telefono; }

    /** @return dirección física del usuario */
    public String getDireccion() { return direccion; }

    /** @param direccion dirección física del usuario */
    public void setDireccion(String direccion) { this.direccion = direccion; }

    /** @return ciudad de residencia del usuario */
    public String getCiudad() { return ciudad; }

    /** @param ciudad ciudad de residencia del usuario */
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

    /** @return número de seguro social (u otro identificador) */
    public String getSsn() { return ssn; }

    /** @param ssn número de seguro social (u otro identificador) */
    public void setSsn(String ssn) { this.ssn = ssn; }

    /** @return código de verificación actual */
    public String getCodigoVerificacion() { return codigoVerificacion; }

    /** @param codigoVerificacion código de verificación actual */
    public void setCodigoVerificacion(String codigoVerificacion) { this.codigoVerificacion = codigoVerificacion; }

    /** @return fecha y hora de expiración del código de verificación */
    public LocalDateTime getExpiracionCodigo() { return expiracionCodigo; }

    /** @param expiracionCodigo fecha y hora de expiración del código de verificación */
    public void setExpiracionCodigo(LocalDateTime expiracionCodigo) { this.expiracionCodigo = expiracionCodigo; }

    /** @return {@code true} si el usuario ha sido verificado */
    public boolean isVerificado() { return verificado; }

    /** @param verificado {@code true} si el usuario ha sido verificado */
    public void setVerificado(boolean verificado) { this.verificado = verificado; }

    /** @return código de verificación para login */
    public String getLoginCodigoVerificacion() { return loginCodigoVerificacion; }

    /** @param loginCodigoVerificacion código de verificación para login */
    public void setLoginCodigoVerificacion(String loginCodigoVerificacion) { this.loginCodigoVerificacion = loginCodigoVerificacion; }

    /** @return fecha y hora de expiración del código de login */
    public LocalDateTime getLoginExpiracionCodigo() { return loginExpiracionCodigo; }

    /** @param loginExpiracionCodigo fecha y hora de expiración del código de login */
    public void setLoginExpiracionCodigo(LocalDateTime loginExpiracionCodigo) { this.loginExpiracionCodigo = loginExpiracionCodigo; }

    /** @return {@code true} si el login ha sido verificado */
    public boolean isLoginVerificado() { return loginVerificado; }

    /** @param loginVerificado {@code true} si el login ha sido verificado */
    public void setLoginVerificado(boolean loginVerificado) { this.loginVerificado = loginVerificado; }

    /** @return contraseña del usuario */
    public String getPassword() { return password; }

    /** @param password contraseña del usuario */
    public void setPassword(String password) { this.password = password; }

    /** @return {@code true} si el usuario tiene una suscripción premium */
    public boolean isEsPremium() { return esPremium; }

    /** @param esPremium {@code true} si el usuario tiene una suscripción premium */
    public void setEsPremium(boolean esPremium) { this.esPremium = esPremium; }

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
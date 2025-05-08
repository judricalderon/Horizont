package co.edu.unbosque.horizont.dto;

public class UsuarioDTO {
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String direccion;
    private String ciudad;
    private String estado;
    private String codigoPostal;
    private String pais;
    private String fechaNacimiento;
    private String ssn;

    public UsuarioDTO() {}

    public UsuarioDTO(String nombre, String apellido, String correo, String telefono,
                      String direccion, String ciudad, String estado, String codigoPostal,
                      String pais, String fechaNacimiento, String ssn) {
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
    }

    // Getters y setters

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getSsn() { return ssn; }
    public void setSsn(String ssn) { this.ssn = ssn; }
}
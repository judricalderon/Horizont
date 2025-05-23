package co.edu.unbosque.horizont.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "posicion")
public class Posicion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    private String simbolo;
    private int cantidad;

    public Posicion() {
    }

    public Posicion(Long id, Usuario usuario, String simbolo, int cantidad) {
        this.id = id;
        this.usuario = usuario;
        this.simbolo = simbolo;
        this.cantidad = cantidad;
    }

    public Posicion(Usuario usuario, String simbolo, int cantidad) {
        this.usuario = usuario;
        this.simbolo = simbolo;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}

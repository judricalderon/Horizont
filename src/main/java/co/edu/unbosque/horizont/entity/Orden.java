package co.edu.unbosque.horizont.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String simbolo;

    private int cantidad;

    @Enumerated(EnumType.STRING)
    private TipoOrden tipo; // COMPRA o VENTA

    private double precioUnitario;

    private double total;

    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    private EstadoOrden estado; // PENDIENTE, EJECUTADA, FALLIDA

    private String alpacaOrderId; // ID de referencia si se ejecuta en Alpaca

    public Orden() {
    }

    public Orden(Long id, Usuario usuario, String simbolo, int cantidad, TipoOrden tipo, double precioUnitario, double total, LocalDateTime fecha, EstadoOrden estado, String alpacaOrderId) {
        this.id = id;
        this.usuario = usuario;
        this.simbolo = simbolo;
        this.cantidad = cantidad;
        this.tipo = tipo;
        this.precioUnitario = precioUnitario;
        this.total = total;
        this.fecha = fecha;
        this.estado = estado;
        this.alpacaOrderId = alpacaOrderId;
    }

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

    public TipoOrden getTipo() {
        return tipo;
    }

    public void setTipo(TipoOrden tipo) {
        this.tipo = tipo;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public EstadoOrden getEstado() {
        return estado;
    }

    public void setEstado(EstadoOrden estado) {
        this.estado = estado;
    }

    public String getAlpacaOrderId() {
        return alpacaOrderId;
    }

    public void setAlpacaOrderId(String alpacaOrderId) {
        this.alpacaOrderId = alpacaOrderId;
    }
}
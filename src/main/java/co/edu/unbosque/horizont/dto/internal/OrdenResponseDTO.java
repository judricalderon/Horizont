package co.edu.unbosque.horizont.dto.internal;

import co.edu.unbosque.horizont.entity.TipoOrden;

public class OrdenResponseDTO {
    private String simbolo;
    private int cantidad;
    private TipoOrden tipo;
    private double precioEjecutado;
    private double totalFinal;
    private String estado;

    public OrdenResponseDTO() {}

    public OrdenResponseDTO(String simbolo, int cantidad, TipoOrden tipo, double precioEjecutado, double totalFinal, String estado) {
        this.simbolo = simbolo;
        this.cantidad = cantidad;
        this.tipo = tipo;
        this.precioEjecutado = precioEjecutado;
        this.totalFinal = totalFinal;
        this.estado = estado;
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

    public double getPrecioEjecutado() {
        return precioEjecutado;
    }

    public void setPrecioEjecutado(double precioEjecutado) {
        this.precioEjecutado = precioEjecutado;
    }

    public double getTotalFinal() {
        return totalFinal;
    }

    public void setTotalFinal(double totalFinal) {
        this.totalFinal = totalFinal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

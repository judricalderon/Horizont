package co.edu.unbosque.horizont.dto.internal;

import co.edu.unbosque.horizont.entity.TipoOrden;

public class OrdenResumenDTO {
    private String simbolo;
    private int cantidad;
    private TipoOrden tipo;
    private double precioActual;
    private double totalEstimado;

    public OrdenResumenDTO() {
    }

    public OrdenResumenDTO(String simbolo, int cantidad, TipoOrden tipo, double precioActual, double totalEstimado) {
        this.simbolo = simbolo;
        this.cantidad = cantidad;
        this.tipo = tipo;
        this.precioActual = precioActual;
        this.totalEstimado = totalEstimado;
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

    public double getPrecioActual() {
        return precioActual;
    }

    public void setPrecioActual(double precioActual) {
        this.precioActual = precioActual;
    }

    public double getTotalEstimado() {
        return totalEstimado;
    }

    public void setTotalEstimado(double totalEstimado) {
        this.totalEstimado = totalEstimado;
    }
}
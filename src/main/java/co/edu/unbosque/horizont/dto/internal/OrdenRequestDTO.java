package co.edu.unbosque.horizont.dto.internal;

import co.edu.unbosque.horizont.entity.TipoOrden;

public class OrdenRequestDTO {
    private String simbolo;
    private int cantidad;
    private TipoOrden tipo;

    public OrdenRequestDTO() {
    }

    public OrdenRequestDTO(String simbolo, int cantidad, TipoOrden tipo) {
        this.simbolo = simbolo;
        this.cantidad = cantidad;
        this.tipo = tipo;
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
}
package co.edu.unbosque.horizont.dto.client.alpaca;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representa la respuesta de Alpaca tras hacer una orden.
 */
public class AlpacaOrderResultDTO {

    /** El identificador de la orden en Alpaca */
    private String id;

    /** Símbolo operado (ej. "AAPL") */
    private String symbol;

    /** Precio promedio al que se ejecutó (mapeado desde filled_avg_price) */
    @JsonProperty("filled_avg_price")
    private double filledAvgPrice;

    /** Cantidad ejecutada (mapeado desde filled_qty) */
    @JsonProperty("filled_qty")
    private int filledQty;

    // getters & setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getFilledAvgPrice() {
        return filledAvgPrice;
    }

    public void setFilledAvgPrice(double filledAvgPrice) {
        this.filledAvgPrice = filledAvgPrice;
    }

    public int getFilledQty() {
        return filledQty;
    }

    public void setFilledQty(int filledQty) {
        this.filledQty = filledQty;
    }
}


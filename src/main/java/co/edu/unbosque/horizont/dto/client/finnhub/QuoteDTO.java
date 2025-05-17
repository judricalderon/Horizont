package co.edu.unbosque.horizont.dto.client.finnhub;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * DTO que representa los datos de una cotización obtenida del servicio Finnhub.
 *
 * Contiene información del precio actual, precio máximo, precio mínimo, precio de apertura
 * y precio de cierre anterior de un activo financiero.
 */
public class QuoteDTO {

    /** Precio actual del activo. */
    @JsonProperty("c")
    private double currentPrice;

    /** Precio máximo del activo en la jornada actual. */
    @JsonProperty("h")
    private double highPrice;

    /** Precio mínimo del activo en la jornada actual. */
    @JsonProperty("l")
    private double lowPrice;

    /** Precio de apertura del activo en la jornada actual. */
    @JsonProperty("o")
    private double openPrice;

    /** Precio de cierre anterior del activo. */
    @JsonProperty("pc")
    private double previousClosePrice;

    public QuoteDTO() {
    }
// Getters y Setters

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getPreviousClosePrice() {
        return previousClosePrice;
    }

    public void setPreviousClosePrice(double previousClosePrice) {
        this.previousClosePrice = previousClosePrice;
    }
}

package co.edu.unbosque.horizont.dto.client.finnhub;
/**
 * DTO que representa la relación entre un símbolo de activo financiero y sus datos de cotización.
 *
 * Combina el identificador del activo (símbolo) con la información de precios obtenida del servicio de cotizaciones.
 */
public class QuoteWithSymbolDTO {

    /** Símbolo del activo financiero (por ejemplo, "AAPL", "GOOG"). */
    private String symbol;

    /** Datos de la cotización del activo asociados al símbolo. */
    private QuoteDTO quote;

    /**
     * Constructor vacío requerido para la deserialización.
     */
    public QuoteWithSymbolDTO() {}
    /**
     * Constructor que inicializa el DTO con un símbolo y sus datos de cotización.
     *
     * @param symbol símbolo del activo financiero.
     * @param quote objeto {@link QuoteDTO} que contiene los datos de cotización del activo.
     */
    public QuoteWithSymbolDTO(String symbol, QuoteDTO quote) {
        this.symbol = symbol;
        this.quote = quote;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public QuoteDTO getQuote() {
        return quote;
    }

    public void setQuote(QuoteDTO quote) {
        this.quote = quote;
    }
}

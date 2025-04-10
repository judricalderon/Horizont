package co.edu.unbosque.horizont.dto.client.finnhub;

public class QuoteWithSymbolDTO {

    private String symbol;
    private QuoteDTO quote;

    public QuoteWithSymbolDTO() {}

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

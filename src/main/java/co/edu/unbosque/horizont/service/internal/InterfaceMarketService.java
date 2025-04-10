package co.edu.unbosque.horizont.service.internal;

import co.edu.unbosque.horizont.dto.client.finnhub.QuoteWithSymbolDTO;

import java.util.List;

public interface InterfaceMarketService {
    List<QuoteWithSymbolDTO> getRealtimeQuotes(List<String> symbols);
}
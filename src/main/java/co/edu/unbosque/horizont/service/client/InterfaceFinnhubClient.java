package co.edu.unbosque.horizont.service.client;

import co.edu.unbosque.horizont.dto.client.finnhub.QuoteDTO;

public interface InterfaceFinnhubClient {
QuoteDTO getQuote(String symbol);
}
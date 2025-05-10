package co.edu.unbosque.horizont.service;


import co.edu.unbosque.horizont.dto.client.finnhub.QuoteDTO;
import co.edu.unbosque.horizont.dto.client.finnhub.QuoteWithSymbolDTO;
import co.edu.unbosque.horizont.service.client.finnhub.InterfaceFinnhubClient;
import co.edu.unbosque.horizont.service.internal.MarketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MarketServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(MarketServiceTest.class);
    private InterfaceFinnhubClient finnhubClient;
    private MarketService marketService;


    @BeforeEach
    void setUp() {
        finnhubClient = mock(InterfaceFinnhubClient.class);
        marketService = new MarketService(finnhubClient);
    }

    @Test
    void shouldReturnQuotesSuccessfully() {
        // Arrange
        QuoteDTO aaplQuote = new QuoteDTO();
        aaplQuote.setCurrentPrice(150.0);
        aaplQuote.setHighPrice(152.0);
        aaplQuote.setLowPrice(147.0);
        aaplQuote.setOpenPrice(148.0);
        aaplQuote.setPreviousClosePrice(149.0);

        QuoteDTO msftQuote = new QuoteDTO();
        msftQuote.setCurrentPrice(310.0);
        msftQuote.setHighPrice(315.0);
        msftQuote.setLowPrice(305.0);
        msftQuote.setOpenPrice(308.0);
        msftQuote.setPreviousClosePrice(309.0);

        when(finnhubClient.getQuote("AAPL")).thenReturn(aaplQuote);
        when(finnhubClient.getQuote("MSFT")).thenReturn(msftQuote);

        // Act
        List<QuoteWithSymbolDTO> result = marketService.getRealtimeQuotes(List.of("AAPL", "MSFT"));

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getSymbol()).isEqualTo("AAPL");
        assertThat(result.get(0).getQuote().getCurrentPrice()).isEqualTo(150.0);
        assertThat(result.get(1).getSymbol()).isEqualTo("MSFT");
        assertThat(result.get(1).getQuote().getCurrentPrice()).isEqualTo(310.0);
        logger.info("la prueba 'shouldReturnQuotesWithSymbols' se realizó exitosamente.");
    }
}

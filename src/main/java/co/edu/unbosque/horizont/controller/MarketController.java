package co.edu.unbosque.horizont.controller;
import co.edu.unbosque.horizont.dto.client.finnhub.QuoteWithSymbolDTO;
import co.edu.unbosque.horizont.service.internal.InterfaceMarketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/market")
public class MarketController {

    private final InterfaceMarketService marketService;

    public MarketController(InterfaceMarketService marketService) {
        this.marketService = marketService;
    }

    @GetMapping("/quotes")
    public ResponseEntity<List<QuoteWithSymbolDTO>> getQuotes(@RequestParam List<String> symbols) {
        List<QuoteWithSymbolDTO> quotes = marketService.getRealtimeQuotes(symbols);
        return ResponseEntity.ok(quotes);
    }
}

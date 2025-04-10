package co.edu.unbosque.horizont.controller;

import co.edu.unbosque.horizont.dto.client.finnhub.QuoteWithSymbolDTO;
import co.edu.unbosque.horizont.service.internal.InterfaceMarketService;
import co.edu.unbosque.horizont.service.internal.InterfaceWatchlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/market")
public class MarketController {

    private final InterfaceMarketService marketService;
    private final InterfaceWatchlistService watchlistService;

    // Constructor con ambos servicios inyectados
    public MarketController(InterfaceMarketService marketService, InterfaceWatchlistService watchlistService) {
        this.marketService = marketService;
        this.watchlistService = watchlistService;
    }

    @GetMapping("/quotes")
    public ResponseEntity<List<QuoteWithSymbolDTO>> getQuotes(@RequestParam List<String> symbols) {
        List<QuoteWithSymbolDTO> quotes = marketService.getRealtimeQuotes(symbols);
        return ResponseEntity.ok(quotes);
    }

    @PostMapping("/watchlist")
    public ResponseEntity<String> saveWatchlist(@RequestBody List<String> symbols) {
        //  Guardar en el servicio de watchlist
        watchlistService.saveWatchlist(symbols);
        return ResponseEntity.ok("Watchlist guardada correctamente");
    }

    @GetMapping("/watchlist")
    public ResponseEntity<List<QuoteWithSymbolDTO>> getWatchlist() {
        List<String> symbols = watchlistService.getWatchlist();
        List<QuoteWithSymbolDTO> quotes = marketService.getRealtimeQuotes(symbols);
        return ResponseEntity.ok(quotes);
    }
}

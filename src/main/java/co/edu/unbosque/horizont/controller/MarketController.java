package co.edu.unbosque.horizont.controller;

import co.edu.unbosque.horizont.dto.client.finnhub.QuoteWithSymbolDTO;
import co.edu.unbosque.horizont.service.internal.InterfaceMarketService;
import co.edu.unbosque.horizont.service.internal.InterfaceWatchlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para operaciones relacionadas con el mercado financiero.
 *
 * Gestiona las solicitudes HTTP que involucran la obtención de cotizaciones en tiempo real
 * y la gestión de la Watchlist asociada a cada usuario.
 */
@RestController
@RequestMapping("/api/market")
public class MarketController {

    private final InterfaceMarketService marketService;
    private final InterfaceWatchlistService watchlistService;

    public MarketController(InterfaceMarketService marketService,
                            InterfaceWatchlistService watchlistService) {
        this.marketService = marketService;
        this.watchlistService = watchlistService;
    }

    /**
     * Endpoint para obtener cotizaciones en tiempo real de una lista arbitraria de símbolos.
     * Ejemplo: GET /api/market/quotes?symbols=AAPL,GOOGL,MSFT
     */
    @GetMapping("/quotes")
    public ResponseEntity<List<QuoteWithSymbolDTO>> getQuotes(
            @RequestParam("symbols") List<String> symbols) {
        List<QuoteWithSymbolDTO> quotes = marketService.getRealtimeQuotes(symbols);
        return ResponseEntity.ok(quotes);
    }

    /**
     * Guarda o actualiza la Watchlist de un usuario premium.
     * Recibe el ID del usuario (por ej. de la sesión) y la lista de símbolos a guardar.
     *
     * POST /api/market/watchlist?usuarioId=42
     * Body: ["AAPL","TSLA","GOOGL"]
     */
    @PostMapping("/watchlist")
    public ResponseEntity<?> saveWatchlist(
            @RequestParam("usuarioId") Long usuarioId,
            @RequestBody List<String> symbols) {

        watchlistService.saveWatchlist(usuarioId, symbols);
        return ResponseEntity.ok("Watchlist guardada correctamente");
    }

    /**
     * Obtiene la Watchlist de un usuario premium y devuelve sus cotizaciones en tiempo real.
     *
     * GET /api/market/watchlist?usuarioId=42
     */
    @GetMapping("/watchlist")
    public ResponseEntity<List<QuoteWithSymbolDTO>> getWatchlist(
            @RequestParam("usuarioId") Long usuarioId) {

        // 1) Recupero únicamente los símbolos de la watchlist del usuario
        List<String> symbols = watchlistService.getWatchlist(usuarioId);
        // 2) Consulto las cotizaciones de esos símbolos
        List<QuoteWithSymbolDTO> quotes = marketService.getRealtimeQuotes(symbols);
        return ResponseEntity.ok(quotes);
    }
}

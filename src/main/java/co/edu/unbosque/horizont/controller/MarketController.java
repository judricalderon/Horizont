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

    /**
     * Elimina uno o varios símbolos del watchlist de un usuario específico.
     *
     * <p>Este endpoint HTTP DELETE recibe un ID de usuario y una lista de símbolos
     * a eliminar del watchlist correspondiente. Utiliza el servicio {@code watchlistService}
     * para procesar cada símbolo individualmente.</p>
     *
     * @param usuarioId El ID del usuario cuyo watchlist será modificado.
     * @param symbols Lista de símbolos (por ejemplo, acciones o criptomonedas) que se desea eliminar del watchlist.
     * @return {@code ResponseEntity} con mensaje de éxito si la operación fue completada correctamente.
     */

    @DeleteMapping("/watchlist")
    public ResponseEntity<?> deleteFromWatchlist(
            @RequestParam("usuarioId") Long usuarioId,
            @RequestBody List<String> symbols) {
        symbols.forEach(sym -> watchlistService.removeSymbol(usuarioId, sym));
        return ResponseEntity.ok("Símbolos eliminados correctamente");
    }
}

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
 * y otras funciones asociadas al mercado y las listas de seguimiento.
 */
@RestController
@RequestMapping("/api/market")
public class MarketController {

    private final InterfaceMarketService marketService;
    private final InterfaceWatchlistService watchlistService;

    /**
     * Constructor que inyecta los servicios de mercado y lista de seguimiento.
     *
     * @param marketService servicio encargado de las operaciones relacionadas con cotizaciones de mercado.
     * @param watchlistService servicio encargado de la gestión de listas de seguimiento de activos.
     */


    // Constructor con ambos servicios inyectados

    public MarketController(InterfaceMarketService marketService, InterfaceWatchlistService watchlistService) {
        this.marketService = marketService;
        this.watchlistService = watchlistService;
    }
    /**
     * Obtiene cotizaciones en tiempo real para una lista de símbolos proporcionados.
     *
     * Este endpoint permite a los clientes recuperar datos actualizados del mercado
     * para múltiples símbolos en una sola solicitud.
     *
     * @param symbols lista de símbolos de activos financieros (por ejemplo, acciones) para los cuales se requieren las cotizaciones.
     * @return una respuesta HTTP con una lista de objetos {@link QuoteWithSymbolDTO} que contienen las cotizaciones en tiempo real.
     */
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

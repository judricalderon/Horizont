package co.edu.unbosque.horizont.service.internal;

import java.util.List;

public interface InterfaceWatchlistService {
    void saveWatchlist(Long usuarioId, List<String> symbols);
    List<String> getWatchlist(Long usuarioId);
    void removeSymbol(Long usuarioId, String symbol);
}
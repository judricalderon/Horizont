package co.edu.unbosque.horizont.service.internal;

import java.util.List;

public interface InterfaceWatchlistService {
    void saveWatchlist(List<String> symbols);
    List<String> getWatchlist();
}
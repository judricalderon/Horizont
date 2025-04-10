package co.edu.unbosque.horizont.service.internal;


import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WatchlistService implements InterfaceWatchlistService {

    private final Map<String, List<String>> watchlists = new HashMap<>();
    private static final String DEFAULT_USER = "usuario-demo";

    @Override
    public void saveWatchlist(List<String> symbols) {
        if (symbols == null || symbols.isEmpty()) {
            throw new IllegalArgumentException("La lista de símbolos no puede estar vacía.");
        }

        // Filtramos símbolos vacíos o nulos
        List<String> listaLimpia = symbols.stream()
                .filter(s -> s != null && !s.trim().isEmpty())
                .map(String::toUpperCase) // opcional: estandarizamos a mayúsculas
                .distinct()
                .toList();

        if (listaLimpia.isEmpty()) {
            throw new IllegalArgumentException("Todos los símbolos eran inválidos.");
        }

        watchlists.put(DEFAULT_USER, listaLimpia);
    }

    @Override
    public List<String> getWatchlist() {
        return watchlists.getOrDefault(DEFAULT_USER, new ArrayList<>());
    }
}
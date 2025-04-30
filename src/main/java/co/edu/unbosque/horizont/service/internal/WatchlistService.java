package co.edu.unbosque.horizont.service.internal;


import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Servicio para gestionar listas de seguimiento (watchlists) de activos financieros.
 *
 * Actualmente mantiene las listas en memoria para un único usuario de demostración.
 * Permite guardar y recuperar listas de símbolos de activos.
 */
@Service
public class WatchlistService implements InterfaceWatchlistService {

    /** Almacena las listas de seguimiento de los usuarios en memoria. */
    private final Map<String, List<String>> watchlists = new HashMap<>();

    /** Usuario por defecto para la demostración. */
    private static final String DEFAULT_USER = "usuario-demo";

    /**
     * Guarda una lista de símbolos en la lista de seguimiento del usuario por defecto.
     *
     * Valida que la lista no sea nula ni vacía, limpia los símbolos inválidos,
     * estandariza los valores a mayúsculas y elimina duplicados.
     *
     * @param symbols lista de símbolos de activos financieros a guardar.
     * @throws IllegalArgumentException si la lista es nula, vacía o contiene solo símbolos inválidos.
     */


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

    /**
     * Recupera la lista de símbolos de activos financieros del usuario por defecto.
     *
     * @return una lista de símbolos, o una lista vacía si no hay datos guardados.
     */



    @Override
    public List<String> getWatchlist() {
        return watchlists.getOrDefault(DEFAULT_USER, new ArrayList<>());
    }
}
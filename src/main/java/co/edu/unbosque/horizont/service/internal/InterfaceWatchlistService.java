package co.edu.unbosque.horizont.service.internal;

import java.util.List;
/**
 * Interfaz para el servicio de gestión de listas de seguimiento de activos financieros.
 *
 * Define las operaciones para guardar y recuperar listas de símbolos de activos.
 */
public interface InterfaceWatchlistService {
    /**
     * Guarda una lista de símbolos en la lista de seguimiento del usuario.
     *
     * @param symbols lista de símbolos de activos financieros a guardar (por ejemplo, "AAPL", "GOOGL").
     * @throws IllegalArgumentException si la lista es nula, vacía o contiene solo símbolos inválidos.
     */
    void saveWatchlist(List<String> symbols);
    /**
     * Recupera la lista actual de símbolos en la lista de seguimiento del usuario.
     *
     * @return una lista de símbolos de activos financieros, o una lista vacía si no hay datos guardados.
     */
    List<String> getWatchlist();
}
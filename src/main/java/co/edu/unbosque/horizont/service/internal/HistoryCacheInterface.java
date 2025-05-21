package co.edu.unbosque.horizont.service.internal;

import co.edu.unbosque.horizont.dto.client.finnhub.HistoryDTO;

public interface HistoryCacheInterface {
    void save(String symbol, HistoryDTO history);

    HistoryDTO get(String symbol);

    boolean contains(String symbol);

    void clear();

}

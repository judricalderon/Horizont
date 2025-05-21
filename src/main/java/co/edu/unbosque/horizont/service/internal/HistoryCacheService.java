package co.edu.unbosque.horizont.service.internal;

import co.edu.unbosque.horizont.service.internal.HistoryCacheInterface;
import co.edu.unbosque.horizont.dto.client.finnhub.HistoryDTO;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class HistoryCacheService implements HistoryCacheInterface {

    private final Map<String, HistoryDTO> cache = new ConcurrentHashMap<>();

    @Override
    public void save(String symbol, HistoryDTO history) {
        cache.put(symbol, history);
    }

    @Override
    public HistoryDTO get(String symbol) {
        return cache.get(symbol);
    }

    @Override
    public boolean contains(String symbol) {
        return cache.containsKey(symbol);
    }

    @Override
    public void clear() {
        cache.clear();
    }
}

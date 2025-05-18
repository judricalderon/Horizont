package co.edu.unbosque.horizont.repository;

import co.edu.unbosque.horizont.entity.WatchlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WatchlistItemRepository extends JpaRepository<WatchlistItem, Long> {
    List<WatchlistItem> findByUsuarioId(Long usuarioId);
    boolean existsByUsuarioIdAndSymbol(Long usuarioId, String symbol);
}
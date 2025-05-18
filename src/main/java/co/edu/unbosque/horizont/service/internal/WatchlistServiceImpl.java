package co.edu.unbosque.horizont.service.internal;

import co.edu.unbosque.horizont.entity.Usuario;
import co.edu.unbosque.horizont.entity.WatchlistItem;
import co.edu.unbosque.horizont.repository.WatchlistItemRepository;
import co.edu.unbosque.horizont.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WatchlistServiceImpl implements InterfaceWatchlistService {

    private final WatchlistItemRepository repo;
    private final UsuarioRepository usuarioRepo;

    public WatchlistServiceImpl(WatchlistItemRepository repo, UsuarioRepository usuarioRepo) {
        this.repo = repo;
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    @Transactional
    public void saveWatchlist(Long usuarioId, List<String> symbols) {
        Usuario user = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (!user.isEsPremium()) {
            throw new IllegalArgumentException("Solo usuarios premium pueden guardar watchlists");
        }

        // Limpieza y deduplicado
        List<String> clean = symbols.stream()
                .filter(s -> s != null && !s.trim().isEmpty())
                .map(String::toUpperCase)
                .distinct()
                .collect(Collectors.toList());

        for (String sym : clean) {
            if (!repo.existsByUsuarioIdAndSymbol(usuarioId, sym)) {
                WatchlistItem item = new WatchlistItem();
                item.setUsuario(user);
                item.setSymbol(sym);
                repo.save(item);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getWatchlist(Long usuarioId) {
        return repo.findByUsuarioId(usuarioId)
                .stream()
                .map(WatchlistItem::getSymbol)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void removeSymbol(Long usuarioId, String symbol) {
        repo.findByUsuarioId(usuarioId).stream()
                .filter(item -> item.getSymbol().equalsIgnoreCase(symbol))
                .forEach(repo::delete);
    }
}
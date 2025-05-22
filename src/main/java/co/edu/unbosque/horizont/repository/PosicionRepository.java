package co.edu.unbosque.horizont.repository;

import co.edu.unbosque.horizont.entity.Posicion;
import co.edu.unbosque.horizont.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PosicionRepository extends JpaRepository<Posicion, Long> {
    Optional<Posicion> findByUsuarioAndSimbolo(Usuario u, String simbolo);
    List<Posicion> findAllByUsuario(Usuario u);
}

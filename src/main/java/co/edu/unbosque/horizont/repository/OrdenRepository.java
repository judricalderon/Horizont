package co.edu.unbosque.horizont.repository;

import co.edu.unbosque.horizont.entity.Orden;
import co.edu.unbosque.horizont.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdenRepository extends JpaRepository<Orden, Long> {
    List<Orden> findAllByUsuarioOrderByFechaDesc(Usuario u);
}
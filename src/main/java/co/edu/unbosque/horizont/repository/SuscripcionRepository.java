package co.edu.unbosque.horizont.repository;

import co.edu.unbosque.horizont.entity.Suscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad {@link co.edu.unbosque.horizont.entity.Suscripcion}.
 *
 * Esta interfaz extiende {@link JpaRepository}, lo que proporciona una implementación por defecto
 * de operaciones CRUD (crear, leer, actualizar, eliminar), así como capacidades de paginación
 * y ordenamiento sobre la entidad {@code Suscripcion}.
 *
 * También puede ser extendida con consultas personalizadas si se requiere lógica adicional
 * de acceso a datos.
 */

@Repository
public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {

}

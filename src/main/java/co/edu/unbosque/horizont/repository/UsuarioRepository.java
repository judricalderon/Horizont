package co.edu.unbosque.horizont.repository;

import co.edu.unbosque.horizont.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Repositorio para la entidad {@link co.edu.unbosque.horizont.entity.Usuario}.
 *
 * Extiende {@link JpaRepository} para proporcionar operaciones CRUD estándar,
 * así como soporte para paginación y ordenamiento.
 *
 * También incluye métodos personalizados para buscar usuarios por su correo electrónico.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param correo correo electrónico del usuario
     * @return un {@link Optional} que contiene el usuario si fue encontrado, o vacío si no existe
     */
    Optional<Usuario> findByCorreo(String correo);

    /**
     * Verifica si existe un usuario registrado con el correo electrónico especificado.
     *
     * @param correo correo electrónico a verificar
     * @return {@code true} si existe un usuario con ese correo, {@code false} en caso contrario
     */
    boolean existsByCorreo(String correo);
}
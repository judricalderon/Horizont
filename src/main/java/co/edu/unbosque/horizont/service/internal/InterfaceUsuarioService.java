package co.edu.unbosque.horizont.service.internal;

import co.edu.unbosque.horizont.dto.internal.UsuarioDTO;
import co.edu.unbosque.horizont.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface InterfaceUsuarioService {
    UsuarioDTO registrarUsuarioDesdeDTO(UsuarioDTO dto);

    Usuario guardarUsuario(Usuario usuario);

    List<Usuario> obtenerTodosLosUsuarios();

    boolean verificarCodigo(Long idUsuario, String codigo);

    Usuario obtenerUsuarioPorId(Long id);

    void eliminarUsuario(Long id);

    Optional<Usuario> login(String correo, String rawPassword);
}

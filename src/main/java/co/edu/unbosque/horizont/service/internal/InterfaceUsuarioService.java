package co.edu.unbosque.horizont.service.internal;

import co.edu.unbosque.horizont.dto.internal.UsuarioDTO;
import co.edu.unbosque.horizont.entity.Usuario;

import java.util.List;

public interface InterfaceUsuarioService {
    UsuarioDTO registrarUsuarioDesdeDTO(UsuarioDTO dto);

    Usuario guardarUsuario(Usuario usuario);

    List<Usuario> obtenerTodosLosUsuarios();

    Usuario obtenerUsuarioPorId(Long id);

    void eliminarUsuario(Long id);
}

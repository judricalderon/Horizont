package co.edu.unbosque.horizont.service.internal;

import co.edu.unbosque.horizont.dto.internal.UsuarioDTO;
import co.edu.unbosque.horizont.entity.Usuario;

import java.util.Optional;

/**
 * Servicio interno que define las operaciones relacionadas con la gestión de usuarios
 * en el sistema Horizont.
 */

public interface InterfaceUsuarioService {

    /**
     * Registra un nuevo usuario en el sistema a partir de un DTO.
     *
     * @param dto el objeto {@link UsuarioDTO} que contiene los datos del nuevo usuario
     * @return el {@link UsuarioDTO} registrado, posiblemente con información adicional como el ID generado
     */
    UsuarioDTO registrarUsuarioDesdeDTO(UsuarioDTO dto);
    /**
     * Verifica si el código ingresado por el usuario es válido.
     *
     * @param idUsuario el identificador del usuario
     * @param codigo el código que se desea verificar
     * @return {@code true} si el código es válido y corresponde al usuario; {@code false} en caso contrario
     */
    boolean verificarCodigo(Long idUsuario, String codigo);
    /**
     * Realiza el proceso de autenticación de un usuario con sus credenciales.
     *
     * @param correo el correo electrónico del usuario
     * @param rawPassword la contraseña en texto plano proporcionada por el usuario
     * @return un {@link Optional} que contiene el {@link Usuario} autenticado si las credenciales son correctas;
     *         de lo contrario, un {@code Optional.empty()}
     */
    Optional<Usuario> login(String correo, String rawPassword);

    /**
     * Inicia el proceso MFA (Multi-Factor Authentication) para login enviando
     * un código de verificación al usuario.
     *
     * @param idUsuario el identificador del usuario al que se le enviará el código
     * @param correo la dirección de correo electrónico donde se enviará el código
     * @return {@code true} si el envío y registro del código fue exitoso; {@code false} si el usuario no existe
     */
    boolean iniciarLoginMfa(Long idUsuario, String correo);

    /**
     * Verifica el código MFA ingresado durante el proceso de login.
     *
     * @param idUsuario el identificador del usuario que está intentando verificar el código
     * @param codigoIngresado el código de verificación que el usuario ingresó
     * @return {@code true} si el código es correcto y está dentro del tiempo de validez; {@code false} en caso contrario
     */
    boolean verificarLoginMfa(Long idUsuario, String codigoIngresado);

    /**
     * Obtiene el DTO de un usuario por su ID.
     *
     * @param idUsuario el ID del usuario
     * @return el {@link UsuarioDTO} con datos básicos y extendidos
     */
    UsuarioDTO obtenerUsuarioDTOPorId(Long idUsuario);
}

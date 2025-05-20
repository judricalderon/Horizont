package co.edu.unbosque.horizont.exception;

/**
 * Excepción lanzada cuando no se encuentra un usuario al buscar por nombre de usuario o ID.
 */
public class UsernameNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Construye una nueva excepción con un mensaje detallando el error.
     *
     * @param message descripción del motivo de la excepción
     */
    public UsernameNotFoundException(String message) {
        super(message);
    }

    /**
     * Construye una nueva excepción con un mensaje y causa raíz.
     *
     * @param message descripción del motivo de la excepción
     * @param cause   la causa original de esta excepción
     */
    public UsernameNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

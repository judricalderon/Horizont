package co.edu.unbosque.horizont.exception;

/**
 * Excepción lanzada cuando se intenta registrar un usuario con un correo
 * que ya existe en la base de datos.
 */
public class EmailAlreadyExistsException extends RuntimeException {

    /**
     * Construye la excepción con un mensaje descriptivo.
     *
     * @param message mensaje que explica la causa de la excepción
     */
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}

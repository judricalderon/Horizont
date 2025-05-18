package co.edu.unbosque.horizont.service.internal;

public interface InterfaceCorreoService {

    /**
     * Envía un correo con el código de verificación al destinatario.
     *
     * @param destinatario dirección de correo que recibirá el mensaje
     * @param codigo       código de verificación a incluir en el cuerpo del correo
     */
    void enviarCodigoVerificacion(String destinatario, String codigo);
}
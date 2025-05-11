package co.edu.unbosque.horizont.service.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de enviar correos electrónicos desde la aplicación.
 * Actualmente se utiliza para enviar códigos de verificación a nuevos usuarios.
 */
@Service
public class CorreoService {

    /**
     * Cliente de JavaMail para envío de correos.
     */
    @Autowired
    private JavaMailSender mailSender;

    /**
     * Envía un correo con el código de verificación al destinatario.
     *
     * @param destinatario dirección de correo que recibirá el mensaje
     * @param codigo       código de verificación a incluir en el cuerpo del correo
     */
    public void enviarCodigoVerificacion(String destinatario, String codigo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();

        // Remitente configurado en la cuenta del correo de la aplicación
        mensaje.setFrom("horizontfortrading@gmail.com");

        // Destinatario y contenido del mensaje
        mensaje.setTo(destinatario);
        mensaje.setSubject("Código de Verificación - Horizont");
        mensaje.setText("Tu código de verificación es: " + codigo);

        // Envío del correo
        mailSender.send(mensaje);
    }
}

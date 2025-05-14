package co.edu.unbosque.horizont.service.internal;

import co.edu.unbosque.horizont.service.internal.InterfaceCorreoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de enviar correos electrónicos desde la aplicación.
 * Actualmente se utiliza para enviar códigos de verificación a nuevos usuarios.
 */
@Service
public class CorreoService implements InterfaceCorreoService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void enviarCodigoVerificacion(String destinatario, String codigo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom("horizontfortrading@gmail.com");
        mensaje.setTo(destinatario);
        mensaje.setSubject("Código de Verificación - Horizont");
        mensaje.setText("Tu código de verificación es: " + codigo);
        mailSender.send(mensaje);
    }
}

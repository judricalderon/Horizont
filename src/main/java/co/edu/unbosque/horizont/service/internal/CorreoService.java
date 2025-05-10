package co.edu.unbosque.horizont.service.internal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class CorreoService {


    @Autowired
    private JavaMailSender mailSender;


    public void enviarCodigoVerificacion(String destinatario, String codigo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();




        mensaje.setFrom("horizontfortrading@gmail.com");


        mensaje.setTo(destinatario);
        mensaje.setSubject("Código de Verificación - Horizont");
        mensaje.setText("Tu código de verificación es: " + codigo);
        mailSender.send(mensaje);
    }
}

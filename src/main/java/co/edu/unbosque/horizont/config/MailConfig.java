package co.edu.unbosque.horizont.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import java.util.Properties;

/**
 * Clase de configuración para el envío de correos electrónicos mediante el protocolo SMTP,
 * utilizando el servicio de Gmail. Define un bean de {@link JavaMailSender} que puede ser
 * inyectado en otros componentes de la aplicación para enviar correos electrónicos.
 *
 * <p>Este componente utiliza el servidor SMTP de Gmail y requiere credenciales válidas
 * para funcionar correctamente. La configuración incluye autenticación, habilitación de
 * TLS y parámetros de depuración.</p>
 *
 * <p><b>Nota:</b> Por seguridad, se recomienda no almacenar directamente las credenciales
 * en el código fuente. Se deberían externalizar mediante archivos de configuración o
 * variables de entorno.</p>
 */
@Configuration
public class MailConfig {

    /**
     * Define un bean de {@link JavaMailSender} configurado para utilizar el servidor SMTP de Gmail.
     *
     * <p>Este bean se utiliza para enviar correos electrónicos desde la aplicación.
     * Configura el host SMTP, el puerto, las credenciales y otras propiedades necesarias
     * para el envío de correos seguros mediante TLS.</p>
     *
     * @return una instancia configurada de {@link JavaMailSenderImpl}
     */
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // Configura el servidor SMTP (Gmail)
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        // Establece las credenciales del remitente
        mailSender.setUsername("horizontfortrading@gmail.com");
        mailSender.setPassword("kiacduxmshckdypf");

        // Configura las propiedades de JavaMail
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");       // Define el protocolo como SMTP
        props.put("mail.smtp.auth", "true");                // Requiere autenticación
        props.put("mail.smtp.starttls.enable", "true");     // Habilita el uso de TLS
        props.put("mail.debug", "true");                    // Activa el modo debug (útil para desarrollo)
        props.put("mail.smtp.from", "horizontfortrading@gmail.com"); // Dirección "from" en el sobre del correo

        return mailSender;
    }
}

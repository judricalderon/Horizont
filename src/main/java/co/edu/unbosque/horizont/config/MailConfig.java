package co.edu.unbosque.horizont.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


import java.util.Properties;


@Configuration
public class MailConfig {


    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();


        // Configuración del servidor SMTP de Gmail
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);


        // Credenciales de la cuenta que envía los correos
        mailSender.setUsername("horizontfortrading@gmail.com");
        mailSender.setPassword("kiacduxmshckdypf");


        Properties props = mailSender.getJavaMailProperties();
        // Protocolo y seguridad
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");


        // Dirección "From" por defecto (envelope from)
        props.put("mail.smtp.from", "horizontfortrading@gmail.com");


        return mailSender;
    }
}

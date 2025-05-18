package co.edu.unbosque.horizont.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configura la seguridad de la aplicación.
 * <p>
 * Define beans relacionados con la seguridad, como el codificador de contraseñas.
 * </p>
 */

@Configuration
public class SecurityConfig {

    /**
     * Define un bean de {@link PasswordEncoder} que utiliza {@link BCryptPasswordEncoder}
     * para codificar contraseñas de forma segura.
     *
     * @return una instancia de {@link BCryptPasswordEncoder}
     */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
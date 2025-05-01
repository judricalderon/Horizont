package co.edu.unbosque.horizont.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
/**
 * Configuración de CORS para permitir la comunicación entre el frontend en JavaScript y el backend en Spring Boot.
 *
 * Esta clase define las reglas necesarias para que aplicaciones web que se ejecutan en diferentes orígenes
 * (como localhost en distintos puertos) puedan interactuar con el backend sin restricciones de política de mismo origen.
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * Configura las reglas de CORS para permitir solicitudes HTTP del frontend hacia el backend.
     *
     * Se autorizan solicitudes desde "http://localhost:5501" y "http://127.0.0.1:5504",
     * habilitando los métodos GET, POST, PUT y DELETE, y permitiendo cualquier cabecera.
     *
     * @param registry objeto utilizado para registrar las configuraciones de CORS.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5501", "http://127.0.0.1:5504") // agrega ambos
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }

}

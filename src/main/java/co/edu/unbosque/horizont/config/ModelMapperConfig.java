package co.edu.unbosque.horizont.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Clase de configuración para el bean de {@link ModelMapper}.
 * <p>
 * Esta configuración permite tener disponible una instancia única (singleton) de ModelMapper
 * en todo el contexto de la aplicación Spring Boot.
 */
@Configuration
public class ModelMapperConfig {
    /**
     * Configuración de Spring para registrar un bean de {@link ModelMapper}.
     * <p>
     * Este bean permite mapear automáticamente objetos entre diferentes tipos,
     * facilitando la conversión entre entidades y DTOs, entre otros usos.
     * <p>
     * Se puede inyectar en cualquier componente de Spring usando {@code @Autowired}.
     *
     * @return una nueva instancia de {@link ModelMapper}.
     */

@Configuration
public class ModelMapperConfig {


    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
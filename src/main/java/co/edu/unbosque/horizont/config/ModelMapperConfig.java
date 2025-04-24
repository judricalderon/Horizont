package co.edu.unbosque.horizont.config;

import org.modelmapper.ModelMapper; // import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean; // import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration; // import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
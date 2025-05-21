package co.edu.unbosque.horizont;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Clase principal para arrancar la aplicación Spring Boot Horizont.
 *
 * Contiene el método main que inicia el contexto de Spring.
 */
@EnableCaching
@SpringBootApplication
public class HorizontApplication {

	public static void main(String[] args) {
		SpringApplication.run(HorizontApplication.class, args);
	}

}

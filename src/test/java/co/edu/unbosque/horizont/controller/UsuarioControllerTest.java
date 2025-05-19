package co.edu.unbosque.horizont.controller;

import co.edu.unbosque.horizont.dto.internal.UsuarioDTO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UsuarioControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioControllerTest.class);

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldRegisterUserSuccessfully() {
        // Arrange
        UsuarioDTO dto = new UsuarioDTO(
                null,
                "Test2",
                "Test2",
                "test6@gmail.com",
                "531-555-2160",
                "20 N San Mateo Dr",
                "San Mateo",
                "CA",
                "94401",
                "USA",
                LocalDate.of(1970, 1, 1),
                "444-55-4321",
                "ABC123",
                LocalDateTime.now().plusMinutes(10),
                true,
                "pass123",
                false
        );


        String url = "http://localhost:" + port + "/usuarios/registrar";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UsuarioDTO> request = new HttpEntity<>(dto, headers);

        // Act
        ResponseEntity<UsuarioDTO> response = restTemplate.postForEntity(url, request, UsuarioDTO.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getNombre()).isEqualTo(dto.getNombre());

        logger.info("Usuario registrado correctamente: {}", response.getBody());
    }
}

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
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("Maria");
        dto.setApellido("Peez");
        dto.setCorreo("marisdfsdf@example.com");
        dto.setTelefono("531-555-2160");
        dto.setDireccion("Call 123");
        dto.setCiudad("San Mateo");
        dto.setEstado("CA");
        dto.setCodigoPostal("110111");
        dto.setPais("USA");
        dto.setFechaNacimiento("1990-05-10");
        dto.setSsn("111-22-3333");


        String url = "http://localhost:" + port + "/usuarios/registrar";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UsuarioDTO> request = new HttpEntity<>(dto, headers);

        // Act
        ResponseEntity<UsuarioDTO> response = restTemplate.postForEntity(url, request, UsuarioDTO.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getNombre()).isEqualTo("Maria");
        logger.info("Usuario registrado correctamente: {}", response.getBody());
    }
}

package co.edu.unbosque.horizont.service;

import co.edu.unbosque.horizont.dto.internal.UsuarioDTO;
import co.edu.unbosque.horizont.entity.Usuario;
import co.edu.unbosque.horizont.repository.UsuarioRepository;
import co.edu.unbosque.horizont.service.internal.UsuarioService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceTest.class);
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void limpiarBD() {
        usuarioRepository.deleteAll();
    }

    @Test
    @Order(1)
    void registrarUsuarioDesdeDTO_deberiaPersistirYRetornarUsuarioDTO() {
        // Arrange
        UsuarioDTO dto =  new UsuarioDTO(
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

        // Act
        UsuarioDTO registrado = usuarioService.registrarUsuarioDesdeDTO(dto);


        // Assert
        assertThat(registrado).isNotNull();
        assertThat(registrado.getCorreo()).isEqualTo("lucia@example.com");

        List<Usuario> usuarios = usuarioRepository.findAll();
        assertThat(usuarios).hasSize(1);
        assertThat(usuarios.get(0).getApellido()).isEqualTo("Garca");
        logger.info("Test de registro ejecutado con éxito para el usuario: {}", registrado.getCorreo());
    }
}

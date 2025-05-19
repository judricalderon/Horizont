package co.edu.unbosque.horizont.service;

import co.edu.unbosque.horizont.dto.internal.SuscripcionRequestDTO;
import co.edu.unbosque.horizont.dto.internal.UsuarioDTO;
import co.edu.unbosque.horizont.entity.Suscripcion;
import co.edu.unbosque.horizont.entity.Usuario;
import co.edu.unbosque.horizont.repository.SuscripcionRepository;
import co.edu.unbosque.horizont.repository.UsuarioRepository;
import co.edu.unbosque.horizont.service.internal.InterfaceSuscripcionService;
import co.edu.unbosque.horizont.service.internal.UsuarioService;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SuscripcionServiceTest {

    private static final Logger log = LoggerFactory.getLogger(SuscripcionServiceTest.class);

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private InterfaceSuscripcionService suscripcionService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SuscripcionRepository suscripcionRepository;

    @Autowired
    private ModelMapper modelMapper;

    private Usuario usuario;

    @BeforeEach
    void limpiarTablas() {
        suscripcionRepository.deleteAll();
        usuarioRepository.deleteAll();

        UsuarioDTO dto = new UsuarioDTO(
                null,
                "Test2",
                "Test2",
                "testsda7542@gmail.com",
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

        usuario = modelMapper.map(usuarioService.registrarUsuarioDesdeDTO(dto), Usuario.class);

    }

    @Test
    @Order(1)
    void crearSuscripcion_deberiaPersistirSuscripcionYMarcarUsuarioPremium() {
        SuscripcionRequestDTO dto = new SuscripcionRequestDTO();
        dto.setUsuarioId(usuario.getId());
        dto.setTipoPlan("mensual");
        dto.setFechaInicio(LocalDate.now());
        dto.setActivo(true);
        dto.setStripeCustomerId("cus_test_001");
        dto.setStripeSubscriptionId("sub_test_001");

        suscripcionService.crearSuscripcion(dto);

        List<Suscripcion> suscripciones = suscripcionRepository.findAll();
        assertThat(suscripciones).hasSize(1);
        assertThat(suscripciones.get(0).getTipoPlan()).isEqualTo("mensual");
        log.info("✅ Suscripción creada: {}", suscripciones.get(0).getStripeSubscriptionId());

        Usuario actualizado = usuarioRepository.findById(usuario.getId()).orElse(null);
        assertThat(actualizado).isNotNull();
        assertThat(actualizado.isEsPremium()).isTrue();
        log.info("✅ Usuario actualizado como premium: {}", actualizado.getCorreo());
    }
}

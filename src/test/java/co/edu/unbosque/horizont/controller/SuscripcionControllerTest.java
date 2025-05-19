package co.edu.unbosque.horizont.controller;

import co.edu.unbosque.horizont.dto.internal.SuscripcionRequestDTO;
import co.edu.unbosque.horizont.dto.internal.UsuarioDTO;
import co.edu.unbosque.horizont.entity.Usuario;
import co.edu.unbosque.horizont.repository.SuscripcionRepository;
import co.edu.unbosque.horizont.repository.UsuarioRepository;
import co.edu.unbosque.horizont.service.internal.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SuscripcionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired private UsuarioService usuarioService;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private SuscripcionRepository suscripcionRepository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private ObjectMapper objectMapper;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        suscripcionRepository.deleteAll();
        usuarioRepository.deleteAll();

        UsuarioDTO dto = new UsuarioDTO(
                null,
                "Test2",
                "Test2",
                "test_" + UUID.randomUUID() + "@gmail.com",
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
    void crearSuscripcion_deberiaRetornarCreatedYRespuestaValida() throws Exception {
        SuscripcionRequestDTO dto = new SuscripcionRequestDTO();
        dto.setUsuarioId(usuario.getId());
        dto.setTipoPlan("mensual");
        dto.setFechaInicio(LocalDate.now());
        dto.setActivo(true);
        dto.setStripeCustomerId("cus_test_001");
        dto.setStripeSubscriptionId("sub_test_001");

        mockMvc.perform(post("/api/suscripciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.tipoPlan").value("mensual"))
                .andExpect(jsonPath("$.stripeCustomerId").value("cus_test_001"))
                .andExpect(jsonPath("$.stripeSubscriptionId").value("sub_test_001"))
                .andExpect(jsonPath("$.usuarioId").value(usuario.getId()));
    }
}

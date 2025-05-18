package co.edu.unbosque.horizont.service.internal;

import co.edu.unbosque.horizont.dto.internal.SuscripcionRequestDTO;
import co.edu.unbosque.horizont.dto.internal.SuscripcionResponseDTO;
import co.edu.unbosque.horizont.entity.Suscripcion;
import co.edu.unbosque.horizont.repository.SuscripcionRepository;
import co.edu.unbosque.horizont.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de suscripciones. Gestiona la creación de nuevas suscripciones
 * y actualiza el estado de los usuarios relacionados.
 */

@Transactional
@Service
public class SuscripcionService implements InterfaceSuscripcionService {

    /**
     * Repositorio para acceder y actualizar usuarios.
     */
    @Autowired
    private final UsuarioRepository usuarioRepository;

    /**
     * Repositorio para gestionar las suscripciones.
     */
    private final SuscripcionRepository suscripcionRepository;

    /**
     * Utilitario para convertir entre DTOs y entidades.
     */
    private final ModelMapper modelMapper;

    /**
     * Constructor que inyecta las dependencias necesarias del servicio.
     *
     * @param suscripcionRepository repositorio de suscripciones
     * @param modelMapper utilidad de mapeo de objetos
     * @param usuarioRepository repositorio de usuarios
     */

    public SuscripcionService(SuscripcionRepository suscripcionRepository, ModelMapper modelMapper, UsuarioRepository usuarioRepository) {
        this.suscripcionRepository = suscripcionRepository;
        this.modelMapper = modelMapper;
        this.usuarioRepository = usuarioRepository;
    }
    /**
     * Crea una nueva suscripción y actualiza al usuario relacionado como premium.
     *
     * @param dto objeto con la información necesaria para registrar una suscripción
     * @return DTO con la información de la suscripción creada
     */

    @Override
    public SuscripcionResponseDTO crearSuscripcion(SuscripcionRequestDTO dto) {
        Suscripcion entidad = new Suscripcion();
        entidad.setUsuarioId(dto.getUsuarioId());
        entidad.setTipoPlan(dto.getTipoPlan());
        entidad.setFechaInicio(dto.getFechaInicio());
        entidad.setFechaCancelacion(dto.getFechaCancelacion());
        entidad.setActivo(dto.isActivo());
        entidad.setStripeCustomerId(dto.getStripeCustomerId());
        entidad.setStripeSubscriptionId(dto.getStripeSubscriptionId());

        Suscripcion guardada = suscripcionRepository.save(entidad);

        //  ACTUALIZA el usuario como premium
        usuarioRepository.findById(dto.getUsuarioId()).ifPresent(usuario -> {
            System.out.println("Usuario encontrado: " + usuario.getCorreo()); // o getNombre()
            usuario.setEsPremium(true);
            usuarioRepository.save(usuario);
        });

        return modelMapper.map(guardada, SuscripcionResponseDTO.class);
    }

}

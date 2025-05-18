package co.edu.unbosque.horizont.service.internal;

import co.edu.unbosque.horizont.dto.internal.SuscripcionRequestDTO;
import co.edu.unbosque.horizont.dto.internal.SuscripcionResponseDTO;
import co.edu.unbosque.horizont.entity.Suscripcion;
import co.edu.unbosque.horizont.repository.SuscripcionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SuscripcionService implements InterfaceSuscripcionService {

    private final SuscripcionRepository suscripcionRepository;
    private final ModelMapper modelMapper;

    public SuscripcionService(SuscripcionRepository suscripcionRepository, ModelMapper modelMapper) {
        this.suscripcionRepository = suscripcionRepository;
        this.modelMapper = modelMapper;
    }

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

        System.out.println("Entidad mapeada: " + entidad); // 👈 Añade esto

        Suscripcion guardada = suscripcionRepository.save(entidad);
        return modelMapper.map(guardada, SuscripcionResponseDTO.class);
    }


    @Override
    public SuscripcionResponseDTO obtenerSuscripcionPorId(Long id) {
        Optional<Suscripcion> resultado = suscripcionRepository.findById(id);
        return resultado.map(s -> modelMapper.map(s, SuscripcionResponseDTO.class))
                .orElse(null); // o lanzar excepción personalizada
    }

    @Override
    public List<SuscripcionResponseDTO> listarSuscripciones() {
        return suscripcionRepository.findAll().stream()
                .map(s -> modelMapper.map(s, SuscripcionResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public SuscripcionResponseDTO actualizarSuscripcion(Long id, SuscripcionRequestDTO dto) {
        Optional<Suscripcion> existente = suscripcionRepository.findById(id);
        if (existente.isPresent()) {
            Suscripcion suscripcion = existente.get();
            modelMapper.map(dto, suscripcion);
            Suscripcion actualizada = suscripcionRepository.save(suscripcion);
            return modelMapper.map(actualizada, SuscripcionResponseDTO.class);
        }
        return null;
    }

    @Override
    public void eliminarSuscripcion(Long id) {
        suscripcionRepository.deleteById(id);
    }
}

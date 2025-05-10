package co.edu.unbosque.horizont.service.internal;

import co.edu.unbosque.horizont.dto.client.alpaca.*;
import co.edu.unbosque.horizont.dto.internal.UsuarioDTO;
import co.edu.unbosque.horizont.entity.Usuario;
import co.edu.unbosque.horizont.repository.UsuarioRepository;
import co.edu.unbosque.horizont.service.client.InterfaceAlpacaClient;
import co.edu.unbosque.horizont.service.internal.CorreoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UsuarioService implements InterfaceUsuarioService {

    private final ModelMapper modelMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private InterfaceAlpacaClient alpacaClient;

    @Autowired
    private CorreoService correoService;

    public UsuarioService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UsuarioDTO registrarUsuarioDesdeDTO(UsuarioDTO dto) {
        // Mapear DTO a Entity
        Usuario usuario = modelMapper.map(dto, Usuario.class);

        // Generar código y expiración
        String codigoVerificacion = generarCodigoAleatorio();
        usuario.setCodigoVerificacion(codigoVerificacion);
        usuario.setExpiracionCodigo(LocalDateTime.now().plusMinutes(10));
        usuario.setVerificado(false);

        // Guardar usuario en BD
        Usuario guardado = usuarioRepository.save(usuario);

        // Enviar código por correo
        correoService.enviarCodigoVerificacion(guardado.getCorreo(), codigoVerificacion);

        // Crear y enviar cuenta a Alpaca
        AlpacaAccountDTO cuenta = buildAlpacaAccountDTO(guardado);
        alpacaClient.crearCuenta(cuenta);

        // Mapear y retornar DTO
        return modelMapper.map(guardado, UsuarioDTO.class);
    }

    /**
     * Construye el DTO de alpaca combinando contact, identity, disclosures y agreements.
     */
    private AlpacaAccountDTO buildAlpacaAccountDTO(Usuario u) {
        // Contact
        ContactDTO contact = new ContactDTO();
        contact.setEmail_address(u.getCorreo());
        contact.setPhone_number(u.getTelefono());
        contact.setStreet_address(List.of(u.getDireccion()));
        contact.setCity(u.getCiudad());
        contact.setState(u.getEstado());
        contact.setPostal_code(u.getCodigoPostal());
        contact.setCountry(u.getPais());

        // Identity
        IdentityDTO identity = new IdentityDTO();
        identity.setGiven_name(u.getNombre());
        identity.setFamily_name(u.getApellido());
        // Asumimos que fechaNacimiento no es null
        identity.setDate_of_birth(u.getFechaNacimiento().toString());
        identity.setTax_id(u.getSsn());
        identity.setFunding_source(List.of("employment_income"));

        // Disclosures
        DisclosuresDTO disclosures = new DisclosuresDTO();
        disclosures.setIs_control_person(false);
        disclosures.setIs_affiliated_exchange_or_finra(false);
        disclosures.setIs_politically_exposed(false);
        disclosures.setImmediate_family_exposed(false);

        // Agreements
        String now = ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT);
        String ip = "127.0.0.1";
        AgreementDTO a1 = new AgreementDTO(); a1.setAgreement("account_agreement"); a1.setSigned_at(now); a1.setIp_address(ip);
        AgreementDTO a2 = new AgreementDTO(); a2.setAgreement("margin_agreement"); a2.setSigned_at(now); a2.setIp_address(ip);
        AgreementDTO a3 = new AgreementDTO(); a3.setAgreement("customer_agreement"); a3.setSigned_at(now); a3.setIp_address(ip);

        AlpacaAccountDTO cuenta = new AlpacaAccountDTO();
        cuenta.setContact(contact);
        cuenta.setIdentity(identity);
        cuenta.setDisclosures(disclosures);
        cuenta.setAgreements(List.of(a1, a2, a3));

        return cuenta;
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        // Lógica sin DTO; reusa mismo flujo para mantener compatibilidad
        UsuarioDTO dto = modelMapper.map(usuario, UsuarioDTO.class);
        return modelMapper.map(registrarUsuarioDesdeDTO(dto), Usuario.class);
    }

    @Override
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    private String generarCodigoAleatorio() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 8; i++) sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }
}

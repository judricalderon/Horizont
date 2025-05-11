package co.edu.unbosque.horizont.service.internal;

import co.edu.unbosque.horizont.dto.client.alpaca.*;
import co.edu.unbosque.horizont.dto.internal.UsuarioDTO;
import co.edu.unbosque.horizont.entity.Usuario;
import co.edu.unbosque.horizont.exception.EmailAlreadyExistsException;
import co.edu.unbosque.horizont.repository.UsuarioRepository;
import co.edu.unbosque.horizont.service.client.InterfaceAlpacaClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Servicio para gestionar operaciones de usuario, incluyendo registro, verificación de códigos OTP
 * y sincronización de cuentas con la API de Alpaca.
 */
@Service
public class UsuarioService implements InterfaceUsuarioService {

    private final ModelMapper modelMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private InterfaceAlpacaClient alpacaClient;

    @Autowired
    private CorreoService correoService;

    /**
     * Construye un {@link UsuarioService} inyectando el {@link ModelMapper}.
     *
     * @param modelMapper mapea entre DTOs y entidades.
     */
    public UsuarioService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Registra un nuevo usuario, enviando OTP por correo y creando la cuenta en Alpaca.
     *
     * Pasos:
     * 1. Verifica si el correo ya existe en la base de datos.
     * 2. Mapea DTO a entidad y genera un código de verificación (OTP).
     * 3. Guarda el usuario en BD y envía OTP por correo.
     * 4. Construye y envía los datos de la cuenta a Alpaca.
     * 5. Devuelve el {@link UsuarioDTO} del usuario registrado.
     *
     * @param dto datos de entrada para crear el usuario.
     * @return DTO del usuario creado.
     * @throws EmailAlreadyExistsException si el correo ya está registrado.
     */
    @Override
    public UsuarioDTO registrarUsuarioDesdeDTO(UsuarioDTO dto) {
        if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new EmailAlreadyExistsException(
                    "El correo '" + dto.getCorreo() + "' ya está registrado."
            );
        }

        Usuario usuario = modelMapper.map(dto, Usuario.class);
        String codigoVerificacion = generarCodigoAleatorio();
        usuario.setCodigoVerificacion(codigoVerificacion);
        usuario.setExpiracionCodigo(LocalDateTime.now().plusMinutes(10));
        usuario.setVerificado(false);

        Usuario guardado = usuarioRepository.save(usuario);
        correoService.enviarCodigoVerificacion(guardado.getCorreo(), codigoVerificacion);

        AlpacaAccountDTO cuenta = buildAlpacaAccountDTO(guardado);
        alpacaClient.crearCuenta(cuenta);

        return modelMapper.map(guardado, UsuarioDTO.class);
    }

    /**
     * Verifica el código OTP de un usuario y activa la cuenta si es válido.
     *
     * @param idUsuario    identificador del usuario.
     * @param codigoIngresado código recibido por correo.
     * @return {@code true} si el código es válido y activa la cuenta; {@code false} si es inválido o expirado.
     */
    @Override
    public boolean verificarCodigo(Long idUsuario, String codigoIngresado) {
        Optional<Usuario> opt = usuarioRepository.findById(idUsuario);
        if (opt.isEmpty()) {
            return false;
        }
        Usuario u = opt.get();
        if (u.isVerificado()) {
            return true;
        }
        if (u.getCodigoVerificacion().equals(codigoIngresado)
                && u.getExpiracionCodigo().isAfter(LocalDateTime.now())) {
            u.setVerificado(true);
            usuarioRepository.save(u);
            return true;
        }
        return false;
    }

    /**
     * Registra un usuario reutilizando la lógica de {@link #registrarUsuarioDesdeDTO(UsuarioDTO)}.
     *
     * @param usuario entidad de usuario para guardar.
     * @return entidad de usuario guardada.
     */
    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        UsuarioDTO dto = modelMapper.map(usuario, UsuarioDTO.class);
        UsuarioDTO savedDto = registrarUsuarioDesdeDTO(dto);
        return modelMapper.map(savedDto, Usuario.class);
    }

    /**
     * Obtiene todos los usuarios registrados.
     *
     * @return lista de usuarios.
     */
    @Override
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id identificador del usuario.
     * @return entidad de usuario o {@code null} si no existe.
     */
    @Override
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id identificador del usuario a eliminar.
     */
    @Override
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    /**
     * Construye el DTO de Alpaca combinando contact, identity, disclosures y agreements.
     *
     * @param u entidad de usuario.
     * @return {@link AlpacaAccountDTO} listo para enviar.
     */
    private AlpacaAccountDTO buildAlpacaAccountDTO(Usuario u) {
        ContactDTO contact = new ContactDTO();
        contact.setEmail_address(u.getCorreo());
        contact.setPhone_number(u.getTelefono());
        contact.setStreet_address(List.of(u.getDireccion()));
        contact.setCity(u.getCiudad());
        contact.setState(u.getEstado());
        contact.setPostal_code(u.getCodigoPostal());
        contact.setCountry(u.getPais());

        IdentityDTO identity = new IdentityDTO();
        identity.setGiven_name(u.getNombre());
        identity.setFamily_name(u.getApellido());
        identity.setDate_of_birth(u.getFechaNacimiento().toString());
        identity.setTax_id(u.getSsn());
        identity.setFunding_source(List.of("employment_income"));

        DisclosuresDTO disclosures = new DisclosuresDTO();
        disclosures.setIs_control_person(false);
        disclosures.setIs_affiliated_exchange_or_finra(false);
        disclosures.setIs_politically_exposed(false);
        disclosures.setImmediate_family_exposed(false);

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

    /**
     * Genera un código OTP aleatorio de 8 caracteres.
     *
     * @return cadena alfanumérica.
     */
    private String generarCodigoAleatorio() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }
}

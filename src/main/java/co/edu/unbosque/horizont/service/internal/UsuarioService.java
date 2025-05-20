package co.edu.unbosque.horizont.service.internal;

import co.edu.unbosque.horizont.dto.client.alpaca.*;
import co.edu.unbosque.horizont.dto.internal.UsuarioDTO;
import co.edu.unbosque.horizont.entity.Usuario;
import co.edu.unbosque.horizont.exception.EmailAlreadyExistsException;
import co.edu.unbosque.horizont.exception.UsernameNotFoundException;
import co.edu.unbosque.horizont.repository.UsuarioRepository;
import co.edu.unbosque.horizont.service.client.alpaca.InterfaceAlpacaClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


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

    @Autowired
    private PasswordEncoder passwordEncoder;

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

        // Mapea DTO a entidad
        Usuario usuario = modelMapper.map(dto, Usuario.class);

        // ---Codifica la contraseña antes de guardar---
        String rawPass = dto.getPassword();
        String hashed = passwordEncoder.encode(rawPass);
        usuario.setPassword(hashed);
        // ──────────────────────────────────────────────

        // Genera y asigna el OTP
        String codigoVerificacion = generarCodigoAleatorio();
        usuario.setCodigoVerificacion(codigoVerificacion);
        usuario.setExpiracionCodigo(LocalDateTime.now().plusMinutes(10));
        usuario.setVerificado(false);

        // Guarda usuario y envía código
        Usuario guardado = usuarioRepository.save(usuario);
        correoService.enviarCodigoVerificacion(guardado.getCorreo(), codigoVerificacion);

        // Crea la cuenta en Alpaca
        AlpacaAccountDTO cuenta = buildAlpacaAccountDTO(guardado);
        alpacaClient.crearCuenta(cuenta);

        // Retorna DTO
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




    @Override
    public Optional<Usuario> login(String correo, String rawPassword) {
        return usuarioRepository.findByCorreo(correo)
                .filter(u -> passwordEncoder.matches(rawPassword, u.getPassword()));
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

    // 1) Iniciar MFA
    public boolean iniciarLoginMfa(Long idUsuario, String correo) {
        Optional<Usuario> opt = usuarioRepository.findById(idUsuario);
        if (opt.isEmpty()) return false;
        Usuario u = opt.get();

        String codigo = generarCodigoAleatorio(); // reutiliza tu método
        u.setLoginCodigoVerificacion(codigo);
        u.setLoginExpiracionCodigo(LocalDateTime.now().plusMinutes(5));
        u.setLoginVerificado(false);
        usuarioRepository.save(u);

        correoService.enviarCodigoVerificacion(correo, codigo);
        return true;
    }

    // 2) Verificar MFA
    public boolean verificarLoginMfa(Long idUsuario, String codigoIngresado) {
        Optional<Usuario> opt = usuarioRepository.findById(idUsuario);
        if (opt.isEmpty()) return false;
        Usuario u = opt.get();
        // aquí usamos el isLoginVerificado()
        if (u.isLoginVerificado()) return true;
        if (codigoIngresado.equals(u.getLoginCodigoVerificacion())
                && u.getLoginExpiracionCodigo().isAfter(LocalDateTime.now())) {
            u.setLoginVerificado(true);
            usuarioRepository.save(u);
            return true;
        }
        return false;
    }

    @Override
    public UsuarioDTO obtenerUsuarioDTOPorId(Long idUsuario) {
        Usuario u = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return modelMapper.map(u, UsuarioDTO.class);
    }

}

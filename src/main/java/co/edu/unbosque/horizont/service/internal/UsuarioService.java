package co.edu.unbosque.horizont.service.internal;

import co.edu.unbosque.horizont.dto.client.alpaca.*;
import co.edu.unbosque.horizont.dto.internal.UsuarioDTO;
import co.edu.unbosque.horizont.entity.Usuario;
import co.edu.unbosque.horizont.repository.UsuarioRepository;
import co.edu.unbosque.horizont.service.client.alpaca.InterfaceAlpacaClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class UsuarioService implements InterfaceUsuarioService{

    private final ModelMapper modelMapper;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private InterfaceAlpacaClient interfaceAlpacaClient;

    public UsuarioService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public UsuarioDTO registrarUsuarioDesdeDTO(UsuarioDTO dto) {
        Usuario usuario = modelMapper.map(dto, Usuario.class);
        Usuario guardado = guardarUsuario(usuario); // lógica que ya tienes
        return modelMapper.map(guardado, UsuarioDTO.class);
    }

    public Usuario guardarUsuario(Usuario usuario) {
        // 1. Guardar localmente en la base de datos
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // 2. Crear ContactDTO
        ContactDTO contact = new ContactDTO();
        contact.setEmail_address(usuario.getCorreo());
        contact.setPhone_number(usuario.getTelefono());
        contact.setStreet_address(List.of(usuario.getDireccion()));
        contact.setCity(usuario.getCiudad());
        contact.setState(usuario.getEstado());
        contact.setPostal_code(usuario.getCodigoPostal());
        contact.setCountry(usuario.getPais());

        // 3. Crear IdentityDTO
        IdentityDTO identity = new IdentityDTO();
        identity.setGiven_name(usuario.getNombre());
        identity.setFamily_name(usuario.getApellido());
        identity.setDate_of_birth(usuario.getFechaNacimiento());
        identity.setTax_id(usuario.getSsn());
        identity.setFunding_source(List.of("employment_income"));

        // 4. Crear DisclosuresDTO (valores por defecto: false)
        DisclosuresDTO disclosures = new DisclosuresDTO();
        disclosures.setIs_control_person(false);
        disclosures.setIs_affiliated_exchange_or_finra(false);
        disclosures.setIs_politically_exposed(false);
        disclosures.setImmediate_family_exposed(false);

        // 5. Crear AgreementsDTO (con IP y fecha simulada)
        String now = ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT);
        String ip = "127.0.0.1"; // reemplaza con IP real si la conoces

        AgreementDTO agreement1 = new AgreementDTO();
        agreement1.setAgreement("account_agreement");
        agreement1.setSigned_at(now);
        agreement1.setIp_address(ip);

        AgreementDTO agreement2 = new AgreementDTO();
        agreement2.setAgreement("margin_agreement");
        agreement2.setSigned_at(now);
        agreement2.setIp_address(ip);

        AgreementDTO agreement3 = new AgreementDTO();
        agreement3.setAgreement("customer_agreement");
        agreement3.setSigned_at(now);
        agreement3.setIp_address(ip);



        // 6. Construir objeto final AlpacaAccountDTO
        AlpacaAccountDTO cuentaAlpaca = new AlpacaAccountDTO();
        cuentaAlpaca.setContact(contact);
        cuentaAlpaca.setIdentity(identity);
        cuentaAlpaca.setDisclosures(disclosures);
        cuentaAlpaca.setAgreements(List.of(agreement1, agreement2, agreement3));

        // 7. Enviar a Alpaca
        interfaceAlpacaClient.crearCuenta(cuentaAlpaca);

        return usuarioGuardado;
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
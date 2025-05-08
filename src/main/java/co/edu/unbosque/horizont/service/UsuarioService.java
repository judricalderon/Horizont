package co.edu.unbosque.horizont.service;

import co.edu.unbosque.horizont.entity.Usuario;
import co.edu.unbosque.horizont.repository.UsuarioRepository;
import co.edu.unbosque.horizont.service.client.AlpacaClient;
import co.edu.unbosque.horizont.dto.client.alpaca.AlpacaAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AlpacaClient alpacaClient;

    public Usuario guardarUsuario(Usuario usuario) {

        Usuario usuarioGuardado = usuarioRepository.save(usuario);


        AlpacaAccountDTO cuentaAlpaca = new AlpacaAccountDTO();
        cuentaAlpaca.setFirst_name(usuario.getNombre());
        cuentaAlpaca.setLast_name(usuario.getApellido());
        cuentaAlpaca.setEmail_address(usuario.getCorreo());
        cuentaAlpaca.setPhone_number(usuario.getTelefono());
        cuentaAlpaca.setStreet_address(usuario.getDireccion());
        cuentaAlpaca.setCity(usuario.getCiudad());
        cuentaAlpaca.setState(usuario.getEstado());
        cuentaAlpaca.setPostal_code(usuario.getCodigoPostal());
        cuentaAlpaca.setCountry(usuario.getPais());
        cuentaAlpaca.setDate_of_birth(usuario.getFechaNacimiento());
        cuentaAlpaca.setSsn(usuario.getSsn());
        cuentaAlpaca.setAccount_type("individual");

        alpacaClient.crearCuenta(cuentaAlpaca);

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

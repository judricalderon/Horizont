package co.edu.unbosque.horizont.config;

import co.edu.unbosque.horizont.entity.Usuario;
import co.edu.unbosque.horizont.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository,
                           PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder   = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "horizontfortrading@gmail.com";
        if (usuarioRepository.findByCorreo(adminEmail).isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNombre("ADMIN");
            admin.setApellido("HORIZONT");
            admin.setCorreo(adminEmail);
            admin.setPassword(passwordEncoder.encode("Alexander#333"));
            admin.setEsPremium(true);
            admin.setAdmin(true);
            admin.setTelefono("3192450400");
            admin.setDireccion("N/A");
            admin.setCiudad("NEW YORK");
            admin.setEstado("NEW YORK");
            admin.setCodigoPostal("");
            admin.setPais("USA");
            admin.setFechaNacimiento(LocalDate.now().minusYears(30));
            admin.setSsn("");
            // Marcar como verificado
            admin.setVerificado(true);
            admin.setLoginVerificado(true);
            usuarioRepository.save(admin);
            System.out.println("Usuario administrador creado: " + adminEmail);
        }
    }
}

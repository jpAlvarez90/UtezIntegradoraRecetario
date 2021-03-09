package edu.utez.recetario;

import edu.utez.recetario.model.Rol;
import edu.utez.recetario.model.Usuario;
import edu.utez.recetario.repository.RolRepository;
import edu.utez.recetario.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
class RecetarioApplicationUsuariosTests {

    /*@Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void consultarUsuarioById() {

        Optional<Usuario> usr = usuarioRepository.findById((long) 1);
        Usuario user = null;

        if (usr.isPresent()) user = usr.get();

        System.out.println("Id: "+user.getIdUsuario());
        System.out.println("Usuario: "+ user.getUsuario());
        System.out.println("Password: "+user.getPassword());
        System.out.println("Rol: "+user.getRol().getRol());

        assert (usr.isPresent());

    }

    @Test
    void registrarUsuario() {

        Usuario user = new Usuario();
        Optional<Rol> rol = rolRepository.findById((long) 2);

        user.setRol(rol.get());
        user.setNombre("Test");
        user.setPrimerApellido("Test1");
        user.setSegundoApellido("Test2");
        user.setCorreo("test@gmail.com");
        user.setUsuario("testy");
        user.setFechaRegistro(new Date());

        String pass = passwordEncoder.encode("12345");
        user.setPassword(pass);

        Usuario retorno = usuarioRepository.save(user);

        System.out.println("Id: " + retorno.getIdUsuario());

        assert (retorno.getIdUsuario() != null);

    }

    @Test
    void modificarUsuario() {

        Optional<Usuario> testUser = usuarioRepository.findById((long) 5);
        Usuario usuario = null;

        if (testUser.isPresent()) {
            usuario = testUser.get();
        } else {
            assert (false);
        }

        usuario.setUsuario("TestUser");

        Usuario retorno = usuarioRepository.save(usuario);

        assert (usuario.getUsuario() == retorno.getUsuario());

    }

    @Test
    void eliminarUsuario() {

        usuarioRepository.deleteById((long) 5);

        assert (!usuarioRepository.existsById((long) 5));

    }
*/
}

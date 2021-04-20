package edu.utez.recetario.service;

import edu.utez.recetario.model.DetallesUsuario;
import edu.utez.recetario.model.Usuario;
import edu.utez.recetario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DetallesUsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);

        usuario.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: "+ username));

        return usuario.map(DetallesUsuario::new).get();
    }
}

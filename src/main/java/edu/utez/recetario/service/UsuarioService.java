package edu.utez.recetario.service;

import edu.utez.recetario.model.Usuario;
import edu.utez.recetario.repository.UsuarioRepository;
import edu.utez.recetario.serviceInterface.UsuarioInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UsuarioInterface {

    private UsuarioRepository usuarioRepository;

    private RolService rolService;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, RolService rolService, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolService = rolService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        usuario.setRol(rolService.getRole((long)2));
        usuario.setFechaRegistro(new Date());
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario getUsuarioById(long id) {
        Optional<Usuario> optional = usuarioRepository.findById(id);
        Usuario usuario = null;
        if(optional.isPresent()) {
            usuario = optional.get();
        } else {
            throw new RuntimeException("User not found with for id :: "+id);
        }
        return usuario;
    }

    @Override
    public void deleteUsuarioById(long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Optional<Usuario> getUsuarioByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}

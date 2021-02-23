package edu.utez.recetario.service;

import edu.utez.recetario.model.Usuario;
import edu.utez.recetario.repository.UsuarioRepository;
import edu.utez.recetario.serviceInterface.UsuarioInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UsuarioInterface {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public void saveEmployee(Usuario usuario) {
        usuarioRepository.save(usuario);
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
}

package edu.utez.recetario.serviceInterface;

import edu.utez.recetario.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioInterface {

    List<Usuario> getAllUsuarios();

    Usuario saveUsuario(Usuario usuario);

    Usuario getUsuarioById(long id);

    void deleteUsuarioById(long id);

    Optional<Usuario> getUsuarioByUsername(String username);

}

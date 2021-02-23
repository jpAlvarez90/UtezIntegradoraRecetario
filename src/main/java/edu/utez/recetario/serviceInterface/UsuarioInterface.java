package edu.utez.recetario.serviceInterface;

import edu.utez.recetario.model.Usuario;

import java.util.List;

public interface UsuarioInterface {

    List<Usuario> getAllUsuarios();

    void saveEmployee(Usuario usuario);

    Usuario getUsuarioById(long id);

    void deleteUsuarioById(long id);

}

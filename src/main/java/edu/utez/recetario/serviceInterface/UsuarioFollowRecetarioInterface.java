package edu.utez.recetario.serviceInterface;

import edu.utez.recetario.model.Recetario;
import edu.utez.recetario.model.Usuario;
import edu.utez.recetario.model.UsuarioFollowRecetario;

import java.util.List;

public interface UsuarioFollowRecetarioInterface {

    List<UsuarioFollowRecetario> getAllFollowingRecetarios(Usuario usuario);

    boolean saveUsuarioFollowRecetario(Recetario recetario, Usuario usuario);

}

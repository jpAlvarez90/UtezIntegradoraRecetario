package edu.utez.recetario.serviceInterface;

import edu.utez.recetario.model.Recetario;
import edu.utez.recetario.model.Usuario;

import java.util.List;

public interface RecetarioInterface {

    List<Recetario> getAllRecetarios();

    Recetario saveRecetario(Recetario recetario);

    Recetario getRecetarioById(long id);

    void deleteRecetarioById(long id);

    List<Recetario> getRecetariosByUserId(Usuario usuario);

}

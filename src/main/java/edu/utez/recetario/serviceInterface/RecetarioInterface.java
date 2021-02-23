package edu.utez.recetario.serviceInterface;

import edu.utez.recetario.model.Recetario;

import java.util.List;

public interface RecetarioInterface {

    List<Recetario> getAllRecetarios();

    void saveRecetario(Recetario recetario);

    Recetario getRecetarioById(long id);

    void deleteRecetarioById(long id);

}

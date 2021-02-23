package edu.utez.recetario.serviceInterface;

import edu.utez.recetario.model.Receta;

import java.util.List;

public interface RecetaInterface {

    List<Receta> getAllRecetas();

    void saveReceta(Receta receta);

    Receta getRecetaById(Long id);

    void deleteRecetaById(Long id);

}

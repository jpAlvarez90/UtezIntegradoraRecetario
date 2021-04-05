package edu.utez.recetario.serviceInterface;

import edu.utez.recetario.model.Receta;
import edu.utez.recetario.model.Recetario;

import java.util.List;

public interface RecetaInterface {

    List<Receta> getAllRecetas();

    Receta saveReceta(Receta receta);

    Receta getRecetaById(Long id);

    void deleteRecetaById(Long id);

    List<Receta> getAllRecetasByRecetario(Recetario recetario);

    List<Receta> getAllRecetasByOrderADesc(int limit);

}

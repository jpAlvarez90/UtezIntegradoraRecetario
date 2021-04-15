package edu.utez.recetario.serviceInterface;

import edu.utez.recetario.model.Categoria;
import edu.utez.recetario.model.Receta;
import edu.utez.recetario.model.Recetario;
import edu.utez.recetario.model.SubCategoria;

import java.util.List;

public interface RecetaInterface {

    List<Receta> getAllRecetas();

    Receta saveReceta(Receta receta);

    Receta getRecetaById(Long id);

    void deleteRecetaById(Long id);

    List<Receta> getAllRecetasByRecetario(Recetario recetario);

    List<Receta> getAllRecetasByOrderADesc(int limit);

    List<Receta> getAllRecetasByVistasDesc(int limit);

    int saveVistasReceta(long idReceta);

    List<Receta> getAllRecetasByCategoria(Categoria categoria);

    List<Receta> getAllRecetasBySubCategoria(Categoria categoria, SubCategoria subCategoria);

}

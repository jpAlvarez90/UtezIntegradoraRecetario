package edu.utez.recetario.serviceInterface;

import edu.utez.recetario.model.Categoria;
import edu.utez.recetario.model.SubCategoria;

import java.util.List;

public interface SubCategoriaInterface {

    List<SubCategoria> getAllSubCategorias();

    SubCategoria saveSubCategoria(SubCategoria subCategoria);

    SubCategoria getSubCategoriaById(long id);

    void deleteSubCategoriaById(long id);

    List<SubCategoria> getAllSubcategoriasByCategoria(Categoria categoria);

}

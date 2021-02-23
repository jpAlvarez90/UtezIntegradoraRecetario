package edu.utez.recetario.serviceInterface;

import edu.utez.recetario.model.SubCategoria;

import java.util.List;

public interface SubCategoriaInterface {

    List<SubCategoria> getAllSubCategorias();

    void saveSubCategoria(SubCategoria subCategoria);

    SubCategoria getSubCategoriaById(long id);

    void deleteSubCategoriaById(long id);

}

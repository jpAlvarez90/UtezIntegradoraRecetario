package edu.utez.recetario.serviceInterface;

import edu.utez.recetario.model.Categoria;

import java.util.List;

public interface CategoriaInterface {

    List<Categoria> getAllCategorias();

    Categoria saveCategoria(Categoria categoria);

    Categoria getCategoriaById(long id);

    void deleteCategoriaById(long id);

}

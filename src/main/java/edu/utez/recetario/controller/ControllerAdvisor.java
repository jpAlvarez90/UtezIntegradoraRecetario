package edu.utez.recetario.controller;

import edu.utez.recetario.model.Categoria;
import edu.utez.recetario.model.SubCategoria;
import edu.utez.recetario.service.CategoriaService;
import edu.utez.recetario.service.SubCategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ControllerAdvice
public class ControllerAdvisor {

    private CategoriaService categoriaService;
    private SubCategoriaService subCategoriaService;

    @Autowired
    public ControllerAdvisor(CategoriaService categoriaService, SubCategoriaService subCategoriaService){
        this.categoriaService = categoriaService;
        this.subCategoriaService = subCategoriaService;
    }

    @ModelAttribute("menuCategory")
    public List<Categoria> menuCategory(){
        return categoriaService.getAllCategorias();
    }

}

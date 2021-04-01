package edu.utez.recetario.controller;

import edu.utez.recetario.model.Comentario;
import edu.utez.recetario.model.Receta;
import edu.utez.recetario.model.Recetario;
import edu.utez.recetario.service.ComentarioService;
import edu.utez.recetario.service.RecetaService;
import edu.utez.recetario.service.RecetarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class RecetaController {

    private RecetarioService recetarioService;

    private RecetaService recetaService;

    private ComentarioService comentarioService;

    @Autowired
    public RecetaController(RecetarioService recetarioService, RecetaService recetaService, ComentarioService comentarioService) {
        this.recetarioService = recetarioService;
        this.recetaService = recetaService;
        this.comentarioService = comentarioService;
    }

    @GetMapping("/ver-recetas/{idRecetario}")
    public String verRecetas(@PathVariable("idRecetario") long idRecetario, Model model) {
        Recetario recetario = recetarioService.getRecetarioById(idRecetario);
        List<Receta> recetaList = recetaService.getAllRecetasByRecetario(recetario);

        model.addAttribute("recetaList",recetaList);
        return "views/receta/recetas";
    }

    //@GetMapping("/ver-receta/{idReceta}")
    public String verReceta(@PathVariable("idReceta") long idReceta, @ModelAttribute("comentario") Comentario comentario, Model model) {

        Receta receta = recetaService.getRecetaById(idReceta);
        List<Comentario> comentarios = comentarioService.getComentarioByRecetaId(idReceta);

        model.addAttribute("receta",receta);
        model.addAttribute("comentarios",comentarios);

        // TODO Verificar el archivo html que se maneraja
        return "verReceta";
    }



















}

package edu.utez.recetario.controller;

import edu.utez.recetario.model.*;
import edu.utez.recetario.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {

    private RecetaService recetaService;

    private ComentarioService comentarioService;

    private CalificacionService calificacionService;

    private CategoriaService categoriaService;

    private SubCategoriaService subCategoriaService;

    private UsuarioService usuarioService;

    @Autowired
    public MainController(RecetaService recetaService, ComentarioService comentarioService, CalificacionService calificacionService, CategoriaService categoriaService, SubCategoriaService subCategoriaService) {
        this.recetaService = recetaService;
        this.comentarioService = comentarioService;
        this.calificacionService = calificacionService;
        this.categoriaService = categoriaService;
        this.subCategoriaService = subCategoriaService;
    }

    @GetMapping("/")
    public String main(Model model){
        List<Receta> listaRecetas= recetaService.getAllRecetasByOrderADesc(10);
        model.addAttribute("listaRecetas",listaRecetas);
        return "index";
    }

    @GetMapping("/mejor-calificado")
    public String mejorCalificados(Model model) {

        List<Calificacion> calificacionList = calificacionService.getRecetasByCalificaciones();
        List<Receta> listaRecetas = new ArrayList<>();

        for (Calificacion calificacion: calificacionList) {
            listaRecetas.add(calificacion.getReceta());
        }

        model.addAttribute("listaRecetas",listaRecetas);
        return "index";
    }

    @GetMapping("/mas-buscados")
    public String masBuscados(Model model) {
        List<Receta> listaRecetas = recetaService.getAllRecetasByVistasDesc(10);
        model.addAttribute("listaRecetas",listaRecetas);
        return "index";
    }

    @GetMapping("/f/categoria/{idCategoria}")
    public String porCategoria(@PathVariable("idCategoria") long idCategoria, Model model) {
        Categoria categoria = categoriaService.getCategoriaById(idCategoria);
        List<Receta> listaRecetas = recetaService.getAllRecetasByCategoria(categoria);
        model.addAttribute("listaRecetas",listaRecetas);
        return "index";
    }

    @GetMapping("/f/categoria/{idCategoria}/subcategoria/{idSubCategoria}")
    public String porCategoriaYSubCategoria(@PathVariable("idCategoria") long idCategoria,
                                            @PathVariable("idSubCategoria") long idSubCategoria,
                                            Model model) {
        Categoria categoria = categoriaService.getCategoriaById(idCategoria);
        SubCategoria subCategoria = subCategoriaService.getSubCategoriaById(idSubCategoria);
        List<Receta> listaRecetas = recetaService.getAllRecetasBySubCategoria(categoria, subCategoria);
        model.addAttribute("listaRecetas",listaRecetas);
        return "index";
    }

    @GetMapping("/ver-receta/{idReceta}")
    public String verReceta(@PathVariable("idReceta") long idReceta,
                            @ModelAttribute("comentario") Comentario comentario,
                            Model model) {

        Receta receta = recetaService.getRecetaById(idReceta);
        List<Comentario> comentarios = comentarioService.getComentarioByReceta(receta);

        model.addAttribute("receta",receta);
        model.addAttribute("comentarios",comentarios);

        return "/views/receta/ver_receta";
    }
















}

package edu.utez.recetario.controller;

import edu.utez.recetario.model.Calificacion;
import edu.utez.recetario.model.Comentario;
import edu.utez.recetario.model.Receta;
import edu.utez.recetario.model.Usuario;
import edu.utez.recetario.service.CalificacionService;
import edu.utez.recetario.service.ComentarioService;
import edu.utez.recetario.service.RecetaService;
import edu.utez.recetario.service.UsuarioService;
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

    private UsuarioService usuarioService;

    private String mensaje;

    @Autowired
    public MainController(RecetaService recetaService, ComentarioService comentarioService, CalificacionService calificacionService, UsuarioService usuarioService) {
        this.recetaService = recetaService;
        this.comentarioService = comentarioService;
        this.calificacionService = calificacionService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public String main(Model model){
        try{
            List<Receta> listaRecetas= recetaService.getAllRecetasByOrderADesc(10);
            model.addAttribute("listaRecetas",listaRecetas);
            return "index";
        }catch (Exception e){
            mensaje = usuarioService.codigosError(e.toString());
            System.out.println("Error en el Main Controller -> main"+mensaje);
            model.addAttribute("mensaje",mensaje);
            return "error/error";
        }
    }

    @GetMapping("/mejor-calificado")
    public String mejorCalificados(Model model) {

        try {
            List<Calificacion> calificacionList = calificacionService.getRecetasByCalificaciones();
            List<Receta> listaRecetas = new ArrayList<>();

            for (Calificacion calificacion: calificacionList) {
                listaRecetas.add(calificacion.getReceta());
            }

            model.addAttribute("listaRecetas",listaRecetas);
            return "index";
        }catch (Exception e){
            mensaje = usuarioService.codigosError(e.toString());
            System.out.println("Error en el Main Controller -> mejorCalificadas"+mensaje);
            model.addAttribute("mensaje",mensaje);
            return "error/error";
        }
    }

    @GetMapping("/mas-buscados")
    public String masBuscados(Model model) {
        try{
            List<Receta> listaRecetas = recetaService.getAllRecetasByVistasDesc(10);
            model.addAttribute("listaRecetas",listaRecetas);
            return "index";
        }catch (Exception e){
            mensaje = usuarioService.codigosError(e.toString());
            System.out.println("Error en el Main Controller -> masBuscados"+mensaje);
            model.addAttribute("mensaje",mensaje);
            return "error/error";
        }
    }

    @GetMapping("/{idCategoria}")
    public String porCategoria(@PathVariable("idCategoria") long idCategoria, Model model) {
        try{
            return "index";
        }catch (Exception e){
            mensaje = usuarioService.codigosError(e.toString());
            System.out.println("Error en el Main Controller -> porCategoria"+mensaje);
            model.addAttribute("mensaje",mensaje);
            return "error/error";
        }
    }

    @GetMapping("/ver-receta/{idReceta}")
    public String verReceta(@PathVariable("idReceta") long idReceta,
                            @ModelAttribute("comentario") Comentario comentario,
                            Model model) {

       try{
           Receta receta = recetaService.getRecetaById(idReceta);
           List<Comentario> comentarios = comentarioService.getComentarioByReceta(receta);

           model.addAttribute("receta",receta);
           model.addAttribute("comentarios",comentarios);

           return "/views/receta/ver_receta";
       }catch (Exception e){
           mensaje = usuarioService.codigosError(e.toString());
           System.out.println("Error en el Main Controller -> verReceta"+mensaje);
           model.addAttribute("mensaje",mensaje);
           return "error/error";
       }
    }
















}

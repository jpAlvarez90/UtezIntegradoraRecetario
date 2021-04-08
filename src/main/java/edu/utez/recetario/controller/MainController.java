package edu.utez.recetario.controller;

import edu.utez.recetario.model.Comentario;
import edu.utez.recetario.model.Receta;
import edu.utez.recetario.model.Usuario;
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

import java.util.List;
import java.util.Optional;

@Controller
public class MainController {

    private RecetaService recetaService;

    private ComentarioService comentarioService;

    private UsuarioService usuarioService;

    @Autowired
    public MainController(RecetaService recetaService, ComentarioService comentarioService, UsuarioService usuarioService) {
        this.recetaService = recetaService;
        this.comentarioService = comentarioService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public String main(Model model){
        List<Receta> listaRecetas= recetaService.getAllRecetasByOrderADesc(10);
        model.addAttribute("listaRecetas",listaRecetas);
        return "index";
    }

    @GetMapping("/mejor-calificado")
    public String mejorCalificados() {

        return "index";
    }

    @GetMapping("/mas-buscados")
    public String masBuscados(Model model) {
        List<Receta> listaRecetas = recetaService.getAllRecetasByVistasDesc(10);
        model.addAttribute("listaRecetas",listaRecetas);
        return "index";
    }

    @GetMapping("/{idCategoria}")
    public String porCategoria(@PathVariable("idCategoria") long idCategoria) {
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

    @PostMapping("/realizar-comentario")
    public String realizarComentario(WebRequest webRequest) {

        long idReceta = Long.parseLong(webRequest.getParameter("idReceta"));
        Receta receta = recetaService.getRecetaById(idReceta);

        String comentario = webRequest.getParameter("floatingTextarea2");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Optional<Usuario> tempUsuario = usuarioService.getUsuarioByUsername(username);
        Usuario usuario = tempUsuario.get();

        Comentario comentarioSave = new Comentario(receta,usuario,comentario);

        comentarioService.saveComentario(comentarioSave);

        return "redirect:/ver-receta/"+idReceta;
    }


















}

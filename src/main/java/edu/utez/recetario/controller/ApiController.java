package edu.utez.recetario.controller;

import edu.utez.recetario.DTO.ComentarioDTO;
import edu.utez.recetario.model.*;
import edu.utez.recetario.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Optional;

@Controller
public class ApiController {

    private CategoriaService categoriaService;

    private SubCategoriaService subCategoriaService;

    private RecetaService recetaService;

    private UsuarioService usuarioService;

    private CalificacionService calificacionService;

    private ComentarioService comentarioService;

    @Autowired
    public ApiController(CategoriaService categoriaService, SubCategoriaService subCategoriaService, RecetaService recetaService, UsuarioService usuarioService, CalificacionService calificacionService, ComentarioService comentarioService) {
        this.categoriaService = categoriaService;
        this.subCategoriaService = subCategoriaService;
        this.recetaService = recetaService;
        this.usuarioService = usuarioService;
        this.calificacionService = calificacionService;
        this.comentarioService = comentarioService;
    }

    @RequestMapping(value = "/subcategorias", method = RequestMethod.GET)
    public @ResponseBody
    List<SubCategoria> subCategoriasList(@RequestParam(value = "idCategoria") long idCategoria) {
        Categoria categoria = categoriaService.getCategoriaById(idCategoria);
        List<SubCategoria> subCategoriaList = subCategoriaService.getAllSubcategoriasByCategoria(categoria);
        return subCategoriaList;
    }

    // Aumentar la visita de la receta despues de 5 segundos
    @RequestMapping(value = "/aumentar-vista", method = RequestMethod.GET)
    public @ResponseBody
    boolean aumentarVisita(@RequestParam(value = "idReceta") long idReceta) {

        Receta receta = recetaService.getRecetaById(idReceta);

        int vistas = receta.getVistas();
        vistas = vistas+1;
        receta.setVistas(vistas);
        recetaService.saveReceta(receta);

        return true;
    }

    @RequestMapping(value = "/calificar-receta", method = RequestMethod.POST)
    public String calificarReceta(@RequestParam(value = "idReceta") long idReceta,
                                  @RequestParam(value = "calificacion") int calificacion) {

        System.out.println("Id receta a calificar: "+idReceta);
        System.out.println("Calificacion de la receta: "+calificacion);


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Optional<Usuario> tempUsuario = usuarioService.getUsuarioByUsername(username);
        Usuario usuario = tempUsuario.get();

        Receta receta = recetaService.getRecetaById(idReceta);

        boolean existeCalificacion = calificacionService.existCalificacionByRecetaAndUsuario(receta, usuario);

        System.out.println("Existe la calificacion: "+existeCalificacion);

        if (!existeCalificacion) {
            Calificacion cal = new Calificacion(receta,usuario,calificacion);
            calificacionService.saveCalificacion(cal);
        }

        return "redirect:/ver-receta/"+idReceta;
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

    @PostMapping("/realizar-comentario-test")
    public @ResponseBody ComentarioDTO realizarComentarioTest(@RequestParam("idReceta") long idReceta, @RequestParam("comentario") String comentario) {

        Receta receta = recetaService.getRecetaById(idReceta);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Optional<Usuario> tempUsuario = usuarioService.getUsuarioByUsername(username);
        Usuario usuario = tempUsuario.get();

        Comentario comentarioSave = new Comentario(receta,usuario,comentario);

        Comentario DTO = comentarioService.saveComentario(comentarioSave);

        ComentarioDTO response = new ComentarioDTO(DTO.getReceta().getIdReceta(),
                DTO.getComentario(),
                DTO.getUsuario().getIdUsuario(),
                DTO.getUsuario().getNombre(),
                DTO.getUsuario().getSegundoApellido(),
                DTO.getUsuario().getSegundoApellido());

        return response;
    }

    @RequestMapping(value = "/calificar-receta-test", method = RequestMethod.GET)
    public boolean calificarRecetaTest(@RequestParam(value = "idReceta") long idReceta,
                                  @RequestParam(value = "calificacion") int calificacion) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Optional<Usuario> tempUsuario = usuarioService.getUsuarioByUsername(username);
        Usuario usuario = tempUsuario.get();

        Receta receta = recetaService.getRecetaById(idReceta);

        boolean existeCalificacion = calificacionService.existCalificacionByRecetaAndUsuario(receta, usuario);

        if (!existeCalificacion) {
            Calificacion cal = new Calificacion(receta,usuario,calificacion);
            calificacionService.saveCalificacion(cal);
        }

        return true;
    }

    @RequestMapping(value = "/subcategoriasMenu", method = RequestMethod.GET)
    public @ResponseBody List<SubCategoria> subCategoriasList(@RequestParam(value = "nombreCategoria") String nombreCategoria) {
        Categoria categoria = categoriaService.findByNombre(nombreCategoria);
        List<SubCategoria> subCategoriaList = subCategoriaService.getAllSubcategoriasByCategoria(categoria);
        return subCategoriaList;
    }

}

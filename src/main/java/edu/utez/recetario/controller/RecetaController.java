package edu.utez.recetario.controller;

import edu.utez.recetario.model.*;
import edu.utez.recetario.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;

@Controller
public class RecetaController {

    private RecetarioService recetarioService;

    private RecetaService recetaService;

    private ComentarioService comentarioService;

    private UsuarioService usuarioService;

    private CategoriaService categoriaService;

    private SubCategoriaService subCategoriaService;

    private UsuarioFollowRecetarioService usuarioFollowRecetarioService;

    private CalificacionService calificacionService;

    private AlmacenamientoImagenesService almacenamientoImagenesService;

    private String mensaje;

    @Autowired
    public RecetaController(RecetarioService recetarioService, RecetaService recetaService, ComentarioService comentarioService, UsuarioService usuarioService, CategoriaService categoriaService, SubCategoriaService subCategoriaService, UsuarioFollowRecetarioService usuarioFollowRecetarioService, CalificacionService calificacionService, AlmacenamientoImagenesService almacenamientoImagenesService) {
        this.recetarioService = recetarioService;
        this.recetaService = recetaService;
        this.comentarioService = comentarioService;
        this.usuarioService = usuarioService;
        this.categoriaService = categoriaService;
        this.subCategoriaService = subCategoriaService;
        this.usuarioFollowRecetarioService = usuarioFollowRecetarioService;
        this.calificacionService = calificacionService;
        this.almacenamientoImagenesService = almacenamientoImagenesService;
    }

    @GetMapping("/ver-recetas/{idRecetario}")
    public String verRecetas(@PathVariable("idRecetario") long idRecetario, Model model) {

       try {
           Recetario recetario = recetarioService.getRecetarioById(idRecetario);
           List<Receta> recetaList = recetaService.getAllRecetasByRecetario(recetario);

           model.addAttribute("recetario", recetario);
           model.addAttribute("recetaList",recetaList);
           model.addAttribute("autor", recetario.getUsuario().getNombre() + " " +
                   recetario.getUsuario().getPrimerApellido() + " " +recetario.getUsuario().getSegundoApellido());
           return "views/receta/recetas";
       }catch (Exception e){
           mensaje = usuarioService.codigosError(e.toString());
           System.out.println("Error en el controller de Receta -> verReceta"+mensaje);
           model.addAttribute("mensaje",mensaje);
           return "error/404";
       }
    }

    // Recetas del recetario seguido
    @GetMapping("/ver-recetas-seguidas/{idRecetario}")
    public String verRecetasSeguidas(@PathVariable("idRecetario") long idRecetario, Model model) {

       try {
           Recetario recetario = recetarioService.getRecetarioById(idRecetario);
           List<Receta> recetaList = recetaService.getAllRecetasByRecetario(recetario);

           model.addAttribute("recetario", recetario);
           model.addAttribute("recetaList",recetaList);
           model.addAttribute("autor", recetario.getUsuario().getNombre() + " " +
                   recetario.getUsuario().getPrimerApellido() + " " +recetario.getUsuario().getSegundoApellido());

           return "views/receta/recetas_seguidas";
       }catch (Exception e){
           mensaje = usuarioService.codigosError(e.toString());
           System.out.println("Error en el controller de Receta -> verRecetasSeguidas"+mensaje);
           model.addAttribute("mensaje",mensaje);
           return "error/404";
       }

    }

    @GetMapping("/ver-receta-seguida/{idReceta}")
    public String verRecetaSeguida(@PathVariable("idReceta") long idReceta,
                            Model model) {

        try {
            Receta receta = recetaService.getRecetaById(idReceta);
            model.addAttribute("receta",receta);
            return "/views/receta/ver_receta_seguida";
        }catch (Exception e){
            mensaje = usuarioService.codigosError(e.toString());
            System.out.println("Error en el controller de Receta -> verRecetaSeguida"+mensaje);
            model.addAttribute("mensaje",mensaje);
            return "error/404";
        }
    }

    @GetMapping("/crear-receta/{idRecetario}")
    public String crearReceta(@PathVariable("idRecetario") long idRecetario, Model model, @ModelAttribute("receta") Receta receta) {

       try {
           Recetario recetario = recetarioService.getRecetarioById(idRecetario);
           List<Categoria> categoriaList = categoriaService.getAllCategorias();
           List<SubCategoria> subCategoriaList = subCategoriaService.getAllSubCategorias();
           model.addAttribute("recetario", recetario);
           model.addAttribute("categoriaList", categoriaList);
           model.addAttribute("subCategoriaList", subCategoriaList);

           return "views/receta/formulario";
       }catch (Exception e){
           mensaje = usuarioService.codigosError(e.toString());
           System.out.println("Error en el controller de Receta -> crearReceta"+mensaje);
           model.addAttribute("mensaje",mensaje);
           return "error/404";
       }
    }

    /*@RequestMapping(value = "/subcategorias", method = RequestMethod.GET)
    public @ResponseBody List<SubCategoria> subCategoriasList(@RequestParam(value = "idCategoria") long idCategoria,Model model) {

        Categoria categoria = categoriaService.getCategoriaById(idCategoria);
        List<SubCategoria> subCategoriaList = subCategoriaService.getAllSubcategoriasByCategoria(categoria);
        return subCategoriaList;
    }*/


    @PostMapping("/crear-receta")
    public String crearReceta(@Valid Receta receta,
                              BindingResult bindingResult,
                              WebRequest webRequest,
                              @RequestParam("imgs")MultipartFile[] files, RedirectAttributes redirectAttributes, Errors errors,Model model) {

        try {
            long idCategoria = Long.parseLong(webRequest.getParameter("categoria"));
            Categoria categoria = categoriaService.getCategoriaById(idCategoria);

            long idSubCategoria = Long.parseLong(webRequest.getParameter("subcategoria"));
            SubCategoria subCategoria = null;
            if (idSubCategoria != 0) {
                subCategoria = subCategoriaService.getSubCategoriaById(idSubCategoria);
            }

            long idRecetario = Long.parseLong(webRequest.getParameter("recetario"));
            Recetario recetario = recetarioService.getRecetarioById(idRecetario);

            List<String> fileNames = new ArrayList<>();
            try {
                Arrays.asList(files).stream().forEach(file -> {
                    almacenamientoImagenesService.save(file);
                    fileNames.add(file.getOriginalFilename());
                });

                StringBuilder names = new StringBuilder();
                for (String file: fileNames) {
                    names.append(file+";");
                }
                receta.setImagenes(names.toString());
            } catch (Exception e) {
                receta.setImagenes("default.png");
                System.out.println("No se pudieron subir las imagenes");
            }

            receta.setCategoria(categoria);
            receta.setSubCategoria(subCategoria);
            receta.setRecetario(recetario);
            receta.setFechaPublicacion(new Date());
            if(receta.getCategoria() == null){
                redirectAttributes.addFlashAttribute("errorCat","Hay un error en la receta");
                return "redirect:/ver-recetas/"+idRecetario;
            }

            if(errors.hasErrors()){
                //redirectAttributes.addFlashAttribute("error","Hay un error en la receta");
                return "redirect:/crear-receta/"+idRecetario;
            }
            redirectAttributes.addFlashAttribute("exito","Todo esta bien");
            recetaService.saveReceta(receta);

            return "redirect:/ver-recetas/"+idRecetario;
        }catch (Exception e){
            mensaje = usuarioService.codigosError(e.toString());
            System.out.println("Error en el controller de Receta -> crearReceta"+mensaje);
            model.addAttribute("mensaje",mensaje);
            return "error/404";
        }
    }

    @GetMapping("/editar-receta/{idRecetario}/{idReceta}")
    public String editarReceta(@PathVariable("idRecetario") long idRecetario,
                               @PathVariable("idReceta") long idReceta,
                               @ModelAttribute("receta") Receta receta,
                               Model model) {

        try {
            Recetario recetario = recetarioService.getRecetarioById(idRecetario);
            receta = recetaService.getRecetaById(idReceta);

            List<Categoria> categoriaList = categoriaService.getAllCategorias();
            List<SubCategoria> subCategoriaList = subCategoriaService.getAllSubCategorias();

            model.addAttribute("receta", receta);
            model.addAttribute("recetario", recetario);
            model.addAttribute("categoriaList", categoriaList);
            model.addAttribute("subCategoriaList", subCategoriaList);

            return "views/receta/formulario";
        }catch (Exception e){
            mensaje = usuarioService.codigosError(e.toString());
            System.out.println("Error en el controller de Receta -> editarReceta"+mensaje);
            model.addAttribute("mensaje",mensaje);
            return "error/404";
        }
    }

    @GetMapping("/eliminar-receta/{idRecetario}/{idReceta}")
    public String eliminarReceta(@PathVariable("idRecetario") long idRecetario, @PathVariable("idReceta") long idReceta,Model model) {
        try {
            recetaService.deleteRecetaById(idReceta);
            return "redirect:/ver-recetas/"+idRecetario;
        }catch (Exception e){
            mensaje = usuarioService.codigosError(e.toString());
            System.out.println("Error en el controller de Receta -> eliminarReceta"+mensaje);
            model.addAttribute("mensaje",mensaje);
            return "error/404";
        }
    }


    // El recetario del path param es el que esta en la receta
    @GetMapping("/seguir-recetario/{idRecetario}")
    public String seguirRecetario(@PathVariable("idRecetario") long idRecetario,Model model) {

       try {
           Recetario recetario = recetarioService.getRecetarioById(idRecetario);

           Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
           UserDetails userDetails = (UserDetails) authentication.getPrincipal();
           String username = userDetails.getUsername();

           Optional<Usuario> tempUsuario = usuarioService.getUsuarioByUsername(username);
           Usuario usuario;

           if (tempUsuario.isPresent()) {
               usuario = tempUsuario.get();
               usuarioFollowRecetarioService.saveUsuarioFollowRecetario(recetario,usuario);
           }

           return "redirect:/ver-recetarios";
       }catch (Exception e){
           mensaje = usuarioService.codigosError(e.toString());
           System.out.println("Error en el controller de Receta -> seguirRecetario"+mensaje);
           model.addAttribute("mensaje",mensaje);
           return "error/404";
       }
    }


















}

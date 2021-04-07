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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

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

    private AlmacenamientoImagenesService almacenamientoImagenesService;

    @Autowired
    public RecetaController(RecetarioService recetarioService, RecetaService recetaService, ComentarioService comentarioService, UsuarioService usuarioService, CategoriaService categoriaService, SubCategoriaService subCategoriaService, UsuarioFollowRecetarioService usuarioFollowRecetarioService, AlmacenamientoImagenesService almacenamientoImagenesService) {
        this.recetarioService = recetarioService;
        this.recetaService = recetaService;
        this.comentarioService = comentarioService;
        this.usuarioService = usuarioService;
        this.categoriaService = categoriaService;
        this.subCategoriaService = subCategoriaService;
        this.usuarioFollowRecetarioService = usuarioFollowRecetarioService;
        this.almacenamientoImagenesService = almacenamientoImagenesService;
    }

    @GetMapping("/ver-recetas/{idRecetario}")
    public String verRecetas(@PathVariable("idRecetario") long idRecetario, Model model) {

        Recetario recetario = recetarioService.getRecetarioById(idRecetario);
        List<Receta> recetaList = recetaService.getAllRecetasByRecetario(recetario);

        model.addAttribute("recetario", recetario);
        model.addAttribute("recetaList",recetaList);
        model.addAttribute("autor", recetario.getUsuario().getNombre() + " " +
                recetario.getUsuario().getPrimerApellido() + " " +recetario.getUsuario().getSegundoApellido());
        return "views/receta/recetas";
    }

    // Recetas del recetario seguido
    @GetMapping("/ver-recetas-seguidas/{idRecetario}")
    public String verRecetasSeguidas(@PathVariable("idRecetario") long idRecetario, Model model) {

        Recetario recetario = recetarioService.getRecetarioById(idRecetario);
        List<Receta> recetaList = recetaService.getAllRecetasByRecetario(recetario);

        model.addAttribute("recetario", recetario);
        model.addAttribute("recetaList",recetaList);
        model.addAttribute("autor", recetario.getUsuario().getNombre() + " " +
                recetario.getUsuario().getPrimerApellido() + " " +recetario.getUsuario().getSegundoApellido());

        return "views/receta/recetas_seguidas";
    }

    @GetMapping("/ver-receta-seguida/{idReceta}")
    public String verRecetaSeguida(@PathVariable("idReceta") long idReceta,
                            Model model) {

        Receta receta = recetaService.getRecetaById(idReceta);

        model.addAttribute("receta",receta);

        return "/views/receta/ver_receta_seguida";
    }

    @GetMapping("/crear-receta/{idRecetario}")
    public String crearReceta(@PathVariable("idRecetario") long idRecetario, Model model, @ModelAttribute("receta") Receta receta) {

        Recetario recetario = recetarioService.getRecetarioById(idRecetario);

        List<Categoria> categoriaList = categoriaService.getAllCategorias();
        List<SubCategoria> subCategoriaList = subCategoriaService.getAllSubCategorias();

        model.addAttribute("recetario", recetario);
        model.addAttribute("categoriaList", categoriaList);
        model.addAttribute("subCategoriaList", subCategoriaList);

        return "views/receta/formulario";
    }

    @RequestMapping(value = "/subcategorias", method = RequestMethod.GET)
    public @ResponseBody List<SubCategoria> subCategoriasList(@RequestParam(value = "idCategoria") long idCategoria) {
        Categoria categoria = categoriaService.getCategoriaById(idCategoria);
        List<SubCategoria> subCategoriaList = subCategoriaService.getAllSubcategoriasByCategoria(categoria);
        return subCategoriaList;
    }


    @PostMapping("/crear-receta")
    public String crearReceta(Receta receta,
                              BindingResult bindingResult,
                              WebRequest webRequest,
                              @RequestParam("imgs")MultipartFile[] files) {

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

        recetaService.saveReceta(receta);

        return "redirect:/ver-recetas/"+idRecetario;
    }

    @GetMapping("/editar-receta/{idRecetario}/{idReceta}")
    public String editarReceta(@PathVariable("idRecetario") long idRecetario,
                               @PathVariable("idReceta") long idReceta,
                               @ModelAttribute("receta") Receta receta,
                               Model model) {

        Recetario recetario = recetarioService.getRecetarioById(idRecetario);
        receta = recetaService.getRecetaById(idReceta);

        List<Categoria> categoriaList = categoriaService.getAllCategorias();
        List<SubCategoria> subCategoriaList = subCategoriaService.getAllSubCategorias();

        model.addAttribute("receta", receta);
        model.addAttribute("recetario", recetario);
        model.addAttribute("categoriaList", categoriaList);
        model.addAttribute("subCategoriaList", subCategoriaList);

        return "views/receta/formulario";
    }

    @GetMapping("/eliminar-receta/{idRecetario}/{idReceta}")
    public String eliminarReceta(@PathVariable("idRecetario") long idRecetario, @PathVariable("idReceta") long idReceta) {
        recetaService.deleteRecetaById(idReceta);
        return "redirect:/ver-recetas/"+idRecetario;
    }


    // El recetario del path param es el que esta en la receta
    @GetMapping("/seguir-recetario/{idRecetario}")
    public String seguirRecetario(@PathVariable("idRecetario") long idRecetario) {

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
    }


















}

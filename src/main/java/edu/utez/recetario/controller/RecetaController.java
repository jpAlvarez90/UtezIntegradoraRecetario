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

    private AlmacenamientoImagenesService almacenamientoImagenesService;

    @Autowired
    public RecetaController(RecetarioService recetarioService, RecetaService recetaService, ComentarioService comentarioService, UsuarioService usuarioService, CategoriaService categoriaService, SubCategoriaService subCategoriaService, AlmacenamientoImagenesService almacenamientoImagenesService) {
        this.recetarioService = recetarioService;
        this.recetaService = recetaService;
        this.comentarioService = comentarioService;
        this.usuarioService = usuarioService;
        this.categoriaService = categoriaService;
        this.subCategoriaService = subCategoriaService;
        this.almacenamientoImagenesService = almacenamientoImagenesService;
    }

    @GetMapping("/ver-recetas/{idRecetario}")
    public String verRecetas(@PathVariable("idRecetario") long idRecetario, Model model) {

        Recetario recetario = recetarioService.getRecetarioById(idRecetario);
        List<Receta> recetaList = recetaService.getAllRecetasByRecetario(recetario);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Optional<Usuario> tempUsuario = usuarioService.getUsuarioByUsername(username);
        Usuario usuario = tempUsuario.get();

        model.addAttribute("recetario", recetario);
        model.addAttribute("recetaList",recetaList);
        model.addAttribute("autor", usuario.getNombre() + " " + usuario.getPrimerApellido() + " " +usuario.getSegundoApellido());
        return "views/receta/recetas";
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
        System.out.println(idCategoria);
        Categoria categoria = categoriaService.getCategoriaById(idCategoria);
        System.out.println(categoria.getNombre());
        List<SubCategoria> subCategoriaList = subCategoriaService.getAllSubcategoriasByCategoria(categoria);
        for (SubCategoria sub: subCategoriaList) {
            System.out.println(sub.getNombre());
        }
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
        SubCategoria subCategoria = subCategoriaService.getSubCategoriaById(idSubCategoria);

        long idRecetario = Long.parseLong(webRequest.getParameter("recetario"));
        Recetario recetario = recetarioService.getRecetarioById(idRecetario);

        String names = "";
        try {
            List<String> fileNames = new ArrayList<>();
            Arrays.asList(files).stream().forEach(file -> {
                almacenamientoImagenesService.save(file);
                fileNames.add(file.getOriginalFilename());
                names.concat(file.getOriginalFilename()+";");
            });
        } catch (Exception e) {
            System.out.println("No se pudieron subir las imagenes");
        }

        receta.setCategoria(categoria);
        receta.setSubCategoria(subCategoria);
        receta.setRecetario(recetario);
        receta.setFechaPublicacion(new Date());
        receta.setImagenes(names);

        recetaService.saveReceta(receta);

        return "redirect:/ver-recetas/"+idRecetario;
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

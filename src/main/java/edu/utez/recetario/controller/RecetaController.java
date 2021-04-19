package edu.utez.recetario.controller;

import edu.utez.recetario.model.*;
import edu.utez.recetario.service.*;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    private BitacoraService bitacoraService;

    private String mensaje;

    Logger logger = LoggerFactory.getLogger(RecetaController.class);

    @Autowired
    public RecetaController(RecetarioService recetarioService, RecetaService recetaService, ComentarioService comentarioService, UsuarioService usuarioService, CategoriaService categoriaService, SubCategoriaService subCategoriaService, UsuarioFollowRecetarioService usuarioFollowRecetarioService, CalificacionService calificacionService, AlmacenamientoImagenesService almacenamientoImagenesService,BitacoraService bitacoraService) {
        this.recetarioService = recetarioService;
        this.recetaService = recetaService;
        this.comentarioService = comentarioService;
        this.usuarioService = usuarioService;
        this.categoriaService = categoriaService;
        this.subCategoriaService = subCategoriaService;
        this.usuarioFollowRecetarioService = usuarioFollowRecetarioService;
        this.calificacionService = calificacionService;
        this.almacenamientoImagenesService = almacenamientoImagenesService;
        this.bitacoraService = bitacoraService;
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
           model.addAttribute("mensaje",mensaje);
           return "error/error";
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
           model.addAttribute("mensaje",mensaje);
           return "error/error";
       }

    }

    @GetMapping("/ver-receta-seguida/{idReceta}")
    public String verRecetaSeguida(@PathVariable("idReceta") long idReceta,
                            Model model) {

        try {
            Receta receta = recetaService.getRecetaById(idReceta);
            model.addAttribute("receta",receta);
            return "views/receta/ver_receta_seguida";
        }catch (Exception e){
            mensaje = usuarioService.codigosError(e.toString());
            model.addAttribute("mensaje",mensaje);
            return "error/error";
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
           model.addAttribute("mensaje",mensaje);
           return "error/error";
       }
    }


    @PostMapping("/crear-receta")
    public String crearReceta(@Valid Receta receta,
                              BindingResult bindingResult,
                              WebRequest webRequest,
                              @RequestParam("imgs") MultipartFile[] files,
                              RedirectAttributes redirectAttributes,
                              Errors errors,
                              Model model,
                              Bitacora bitacora,
                              HttpSession session) {

        try {
            long idRecetario = Long.parseLong(webRequest.getParameter("recetario"));
            Recetario recetario = recetarioService.getRecetarioById(idRecetario);

            long idCategoria = Long.parseLong(webRequest.getParameter("categoria"));
            if (idCategoria == 0){
                logger.error("Se debe seleccionar una categoria");
                redirectAttributes.addFlashAttribute("errorCategoria",true);
                return "redirect:/editar-receta/"+idRecetario+"/"+receta.getIdReceta();
            }
            Categoria categoria = categoriaService.getCategoriaById(idCategoria);

            long idSubCategoria = Long.parseLong(webRequest.getParameter("subcategoria"));
            SubCategoria subCategoria = null;

            if (idSubCategoria != 0)
                subCategoria = subCategoriaService.getSubCategoriaById(idSubCategoria);

            if(errors.hasErrors()){
                List<ObjectError> errores = errors.getAllErrors();
                for (ObjectError error: errores) {
                    logger.error(error.getCode() + ": " + error.getDefaultMessage());

                    if (error.getDefaultMessage().equals("Solo se permiten letras mayusculas y minusculas")){
                        logger.error("Solo se pueden usar letras en el titulo y subtitulo");
                        redirectAttributes.addFlashAttribute("errorRecetaSoloTexto",true);
                    }
                    if (error.getDefaultMessage().equals("No se permiten caracteres especiales")){
                        logger.error("No se permiten caracteres especiales (Solo &, !, ¡, ¿, ?)");
                        redirectAttributes.addFlashAttribute("errorRecetaSoloTexto",true);
                    }
                }
                redirectAttributes.addFlashAttribute("errorCMReceta",true);
                return "redirect:/ver-recetas/"+idRecetario;
            }
            List<String> fileNames = new ArrayList<>();
            try {
                String uploadDir = "uploads/"+receta.getTitulo().replaceAll("\\s","");
                try {
                    Path uploadPath = Paths.get(uploadDir);
                    File dirFiles = uploadPath.toFile();
                    FileUtils.deleteDirectory(dirFiles);
                    logger.info("Folder de la receta eliminado");
                } catch (IOException e) {
                    logger.info("No se pudo eliminar la carpeta "+ e.getMessage());
                } catch (IllegalArgumentException e) {
                    logger.info("El folder de la receta no existe "+e.getMessage());
                }

                Arrays.asList(files).stream().forEach(file -> {
                    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                    almacenamientoImagenesService.aSave(file, uploadDir, fileName);
                    fileNames.add(file.getOriginalFilename());
                });

                StringBuilder names = new StringBuilder();
                for (String file: fileNames) {
                    names.append(file+";");
                }
                if (names.toString().equals(";")){
                    receta.setImagenes("default.jpg;");
                } else {
                    receta.setImagenes(names.toString());
                }
            } catch (Exception e) {
                receta.setImagenes("default.jpg;");
            }

            receta.setCategoria(categoria);
            receta.setSubCategoria(subCategoria);
            receta.setRecetario(recetario);
            receta.setFechaPublicacion(new Date());

            redirectAttributes.addFlashAttribute("exito",true);
            if (receta.getIdReceta() != null){
                bitacora.setOperacion("Editar Receta - "+receta.getTitulo());
            }else {
                bitacora.setOperacion("Insertar Receta - " + receta.getTitulo());
            }
            recetaService.saveReceta(receta);
            bitacora.setTabla("Receta");
            bitacoraService.saveBitacora(bitacora);

            return "redirect:/ver-recetas/"+idRecetario;

        }catch (Exception e){
            mensaje = usuarioService.codigosError(e.toString());
            model.addAttribute("mensaje",mensaje);
            return "error/error";
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
            model.addAttribute("mensaje",mensaje);
            return "error/error";
        }
    }

    @GetMapping("/eliminar-receta/{idRecetario}/{idReceta}")
    public String eliminarReceta(@PathVariable("idRecetario") long idRecetario, @PathVariable("idReceta") long idReceta, Bitacora bitacora,Model model, RedirectAttributes redirectAttributes) {
        try {
            Receta receta = recetaService.getRecetaById(idReceta);
            String uploadDir = "uploads/"+receta.getTitulo().replaceAll("\\s","");
            try {
                Path uploadPath = Paths.get(uploadDir);
                File dirFiles = uploadPath.toFile();
                FileUtils.deleteDirectory(dirFiles);
                logger.info("Folder de la receta eliminado");
            } catch (IOException e) {
                logger.info("No se pudo eliminar la carpeta "+ e.getMessage());
            } catch (IllegalArgumentException e) {
                logger.info("El folder de la receta no existe "+e.getMessage());
            }

            recetaService.deleteRecetaById(idReceta);
            redirectAttributes.addFlashAttribute("eliminado",true);
            bitacora.setTabla("Receta");
            bitacora.setOperacion("Eliminar Receta");
            return "redirect:/ver-recetas/"+idRecetario;
        }catch (Exception e){
            mensaje = usuarioService.codigosError(e.toString());
            model.addAttribute("mensaje",mensaje);
            return "error/error";
        }
    }


    // El recetario del path param es el que esta en la receta
    @GetMapping("/seguir-recetario/{idRecetario}")
    public String seguirRecetario(@PathVariable("idRecetario") long idRecetario,Bitacora bitacora,Model model) {

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
               bitacora.setOperacion("Seguir recetario - "+recetario.getNombre()+" por usuario "+username);
               bitacora.setTabla("usuario_follow_recetario");
               bitacoraService.saveBitacora(bitacora);
           }
           return "redirect:/ver-recetarios";
       }catch (Exception e) {
           mensaje = usuarioService.codigosError(e.toString());
                  model.addAttribute("mensaje",mensaje);
           return "error/error";
       }
    }

    @GetMapping("/ver-mi-receta/{idReceta}")
    public String verMiReceta(@PathVariable("idReceta") long idReceta, Model model) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            Optional<Usuario> tempUsuario = usuarioService.getUsuarioByUsername(username);
            Usuario usuario = tempUsuario.get();

            List<Receta> recetaList = recetaService.getLastRecetasByUsuario(usuario.getIdUsuario(),5);

            model.addAttribute("recetaList",recetaList);

            return "views/receta/ver_receta_simple";
        } catch (Exception e){
            mensaje = usuarioService.codigosError(e.toString());
            model.addAttribute("mensaje",mensaje);
            return "error/404";
        }
    }

















}

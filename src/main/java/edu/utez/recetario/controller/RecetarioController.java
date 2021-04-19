package edu.utez.recetario.controller;

import edu.utez.recetario.model.Receta;
import edu.utez.recetario.model.Recetario;
import edu.utez.recetario.model.Usuario;
import edu.utez.recetario.model.UsuarioFollowRecetario;
import edu.utez.recetario.service.RecetaService;
import edu.utez.recetario.service.RecetarioService;
import edu.utez.recetario.service.UsuarioFollowRecetarioService;
import edu.utez.recetario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class RecetarioController {

    private RecetarioService recetarioService;

    private RecetaService recetaService;

    private UsuarioService usuarioService;

    private UsuarioFollowRecetarioService usuarioFollowRecetarioService;

    private String mensaje;

    @Autowired
    public RecetarioController(RecetarioService recetarioService, RecetaService recetaService, UsuarioService usuarioService, UsuarioFollowRecetarioService usuarioFollowRecetarioService) {
        this.recetarioService = recetarioService;
        this.recetaService = recetaService;
        this.usuarioService = usuarioService;
        this.usuarioFollowRecetarioService = usuarioFollowRecetarioService;
    }

    @GetMapping("/ver-recetarios")
    public String misRecetarios(Model model, @ModelAttribute("recetario") Recetario recetario) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            Optional<Usuario> tempUsuario = usuarioService.getUsuarioByUsername(username);
            Usuario usuario;

            if (tempUsuario.isPresent()) {
                usuario = tempUsuario.get();
                List<Recetario> recetarioList = recetarioService.getRecetariosByUserId(usuario);
                List<UsuarioFollowRecetario> ufrList = usuarioFollowRecetarioService.getAllFollowingRecetarios(usuario);
                List<Receta> recetaList = recetaService.getLastRecetasByUsuario(usuario.getIdUsuario(), 5);

                model.addAttribute("recetarioList",recetarioList);
                model.addAttribute("ufrList", ufrList);
                model.addAttribute("recetaList",recetaList);
            } else {
                List<Recetario> recetarioList = new ArrayList<>();
                List<UsuarioFollowRecetario> ufrList = new ArrayList<>();
                List<Receta> recetaList = new ArrayList<>();

                model.addAttribute("recetarioList",recetarioList);
                model.addAttribute("ufrList", ufrList);
                model.addAttribute("recetaList",recetaList);
            }

            return "views/recetario/recetarios";
        }catch (Exception e){
            mensaje = usuarioService.codigosError(e.toString());
            System.out.println("Error en el controller de Recetario -> misRecetarios "+mensaje);
            model.addAttribute("mensaje",mensaje);
            return "error/error";
        }

    }

    @PostMapping("/registrar-recetario")
    public String registrarRecetario(@Valid Recetario recetario, Errors errors, RedirectAttributes ra,Model model) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            Optional<Usuario> tempUsuario = usuarioService.getUsuarioByUsername(username);
            recetario.setUsuario(tempUsuario.get());
            if(errors.hasErrors()){
                ra.addFlashAttribute("mensajeError","Algo esta mal");
                return "redirect:/ver-recetarios";
            }
            ra.addFlashAttribute("exito","Todo salio bien");
            recetarioService.saveRecetario(recetario);
            return "redirect:/ver-recetarios";
        }catch (Exception e){
            mensaje = usuarioService.codigosError(e.toString());
            System.out.println("Error en el controller de Recetario -> registrarRecetario "+mensaje);
            model.addAttribute("mensaje",mensaje);
            return "error/error";
        }
    }

    @GetMapping("/eliminar-recetario/{idRecetario}")
    public String eliminarRecetario(@PathVariable("idRecetario") long idRecetario, RedirectAttributes redirectAttributes, Model model) {

        try{
            Recetario recetario = recetarioService.getRecetarioById(idRecetario);
            List<Receta> recetaList = recetaService.getAllRecetasByRecetario(recetario);
            if (!recetaList.isEmpty()){
                redirectAttributes.addFlashAttribute("eliminarRecetario",true);
                return "redirect:/ver-recetarios";
            }

            recetarioService.deleteRecetarioById(idRecetario);
            redirectAttributes.addFlashAttribute("eliminado",true);

            return "redirect:/ver-recetarios";
        }catch (Exception e){
            mensaje = usuarioService.codigosError(e.toString());
            System.out.println("Error en el controller de Recetario -> Eliminar Recetario "+mensaje);
            model.addAttribute("mensaje",mensaje);
            return "error/error";
        }
    }

    @GetMapping("/dejar-seguir-recetario/{idRecetario}")
    public String dejarSeguirRecetario(@PathVariable("idRecetario") long idRecetario, RedirectAttributes redirectAttributes, Model model) {

       try {
           Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
           UserDetails userDetails = (UserDetails) authentication.getPrincipal();
           String username = userDetails.getUsername();

           Optional<Usuario> tempUsuario = usuarioService.getUsuarioByUsername(username);
           Usuario usuario = tempUsuario.get();

           Recetario recetario = recetarioService.getRecetarioById(idRecetario);

           UsuarioFollowRecetario ufr = usuarioFollowRecetarioService.getUsuarioFollowingRecetario(usuario, recetario);

           usuarioFollowRecetarioService.deleteUsuarioFollowRecetario(ufr);

           redirectAttributes.addFlashAttribute("dejarSeguirRecetario", true);

           return "redirect:/ver-recetarios";
       }catch (Exception e){
           mensaje = usuarioService.codigosError(e.toString());
           System.out.println("Error en el controller de Recetario -> dejarSeguirRecetario "+mensaje);
           model.addAttribute("mensaje",mensaje);
           return "error/error";
       }
    }
}

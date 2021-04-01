package edu.utez.recetario.controller;

import edu.utez.recetario.model.Recetario;
import edu.utez.recetario.model.Usuario;
import edu.utez.recetario.service.RecetarioService;
import edu.utez.recetario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class RecetarioController {

    private RecetarioService recetarioService;

    private UsuarioService usuarioService;

    @Autowired
    public RecetarioController(RecetarioService recetarioService, UsuarioService usuarioService) {
        this.recetarioService = recetarioService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/ver-recetarios")
    public String misRecetarios(Model model, @ModelAttribute("recetario") Recetario recetario) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Optional<Usuario> tempUsuario = usuarioService.getUsuarioByUsername(username);
        Usuario usuario;

        if (tempUsuario.isPresent()) {
            usuario = tempUsuario.get();
            List<Recetario> recetarioList = recetarioService.getRecetariosByUserId(usuario);
            model.addAttribute("recetarioList",recetarioList);
        } else {
            List<Recetario> recetarioList = new ArrayList<>();
            model.addAttribute("recetarioList",recetarioList);
        }

        return "views/recetario/recetarios";
    }

    @PostMapping("/registrar-recetario")
    public String registrarRecetario(Recetario recetario) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Optional<Usuario> tempUsuario = usuarioService.getUsuarioByUsername(username);
        recetario.setUsuario(tempUsuario.get());

        recetarioService.saveRecetario(recetario);
        return "redirect:/ver-recetarios";
    }
}

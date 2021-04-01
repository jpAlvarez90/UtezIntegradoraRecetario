package edu.utez.recetario.login;

import edu.utez.recetario.model.Usuario;
import edu.utez.recetario.service.RolService;
import edu.utez.recetario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class LoginController {

    private UsuarioService usuarioService;

    private RolService rolService;

    @Autowired
    public LoginController(UsuarioService usuarioService, RolService rolService) {
        this.usuarioService = usuarioService;
        this.rolService = rolService;
    }

    @GetMapping("/login")
    public String login(@ModelAttribute("usuario") Usuario usuario) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        return "redirect:/";
    }

    @PostMapping("/login")
    public String auth() {
        return "login";
    }

    @PostMapping("/registrar-usuario")
    public String registrarUsuario(Usuario usuario) {
        usuarioService.saveUsuario(usuario);
        return "redirect:/login";
    }

}

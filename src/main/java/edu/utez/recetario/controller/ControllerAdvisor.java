package edu.utez.recetario.controller;

import edu.utez.recetario.model.Categoria;
import edu.utez.recetario.model.SubCategoria;
import edu.utez.recetario.model.Usuario;
import edu.utez.recetario.service.CategoriaService;
import edu.utez.recetario.service.SubCategoriaService;
import edu.utez.recetario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@ControllerAdvice
public class ControllerAdvisor {

    private CategoriaService categoriaService;

    private SubCategoriaService subCategoriaService;

    private UsuarioService usuarioService;

    @Autowired
    public ControllerAdvisor(CategoriaService categoriaService, SubCategoriaService subCategoriaService, UsuarioService usuarioService) {
        this.categoriaService = categoriaService;
        this.subCategoriaService = subCategoriaService;
        this.usuarioService = usuarioService;
    }

    @ModelAttribute("menuCategory")
    public List<Categoria> menuCategory(){
        return categoriaService.getAllCategorias();
    }

    @ModelAttribute("perfilUsuario")
    public String perfilUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String iniciales = "";
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            Optional<Usuario> tempUsuario = usuarioService.getUsuarioByUsername(username);
            Usuario usuario = tempUsuario.get();

            iniciales = usuario.getNombre().charAt(0) +""+ usuario.getPrimerApellido().charAt(0);
        }
        return iniciales;
    }










}

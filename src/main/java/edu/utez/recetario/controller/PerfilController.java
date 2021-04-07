package edu.utez.recetario.controller;

import edu.utez.recetario.model.Categoria;
import edu.utez.recetario.model.SubCategoria;
import edu.utez.recetario.model.Usuario;
import edu.utez.recetario.service.CategoriaService;
import edu.utez.recetario.service.SubCategoriaService;
import edu.utez.recetario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.thymeleaf.exceptions.TemplateInputException;

import javax.crypto.Cipher;
import java.security.Key;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
public class PerfilController {

    @Autowired
    CategoriaService categoriaService;

    @Autowired
    SubCategoriaService subCategoriaService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/perfil")
    public String configuracion(@ModelAttribute("categoria") Categoria categoria,
                                @ModelAttribute("subcategoria") SubCategoria subcategoria,
                                @ModelAttribute("usuario")Usuario usuario,
                                Model model,
                                BindingResult bindingResult) {

        List<Categoria> listaCategoria = categoriaService.getAllCategorias();
        List<SubCategoria> listaSubcategorias = subCategoriaService.getAllSubCategorias();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Optional<Usuario> tempUsuario = usuarioService.getUsuarioByUsername(username);
        usuario = tempUsuario.get();

        model.addAttribute("usuario",usuario);
        model.addAttribute("listaSubcategoria", listaSubcategorias);
        model.addAttribute("listaCategoria",listaCategoria);
        model.addAttribute("categoria",categoria);
        model.addAttribute("subcategoria", subcategoria);

        return "views/perfil/perfil";
    }

    @PostMapping("/crearCategoria")
    public String crearCategoria (Model model, Categoria categoria, BindingResult result){

        try {
            categoriaService.saveCategoria(categoria);
        }catch (Error e){
            System.out.println("Categoria controller "+e);
        }
        return "redirect:/perfil";
    }

    @GetMapping("/editarCategoria/{idCategoria}")
    public String editarCategoria (@PathVariable("idCategoria") long idCategoria, @ModelAttribute("categoria") Categoria categoria, @ModelAttribute("subcategoria") SubCategoria subcategoria, @ModelAttribute("usuario")Usuario usuario, Model model) {

        categoria = categoriaService.getCategoriaById(idCategoria);
        List<Categoria> listaCategoria = categoriaService.getAllCategorias();
        List<SubCategoria> listaSubcategorias = subCategoriaService.getAllSubCategorias();

        model.addAttribute("listaSubcategoria", listaSubcategorias);
        model.addAttribute("listaCategoria",listaCategoria);
        model.addAttribute("categoria",categoria);

        return "views/perfil/perfil";
    }

    @GetMapping("/eliminarCategoria/{idCategoria}")
    public String eliminarCategoria (@PathVariable("idCategoria") long idCategoria){

        try{
            categoriaService.deleteCategoriaById(idCategoria);
        }catch (Error e){
            System.out.println("Categoria controller "+e);
        }

        return "redirect:/perfil";
    }




    @PostMapping("/crearSubcategoria")
    public String crearSubcategoria ( SubCategoria subCategoria, Categoria categoria, BindingResult bindingResult, Model model, WebRequest request){

        try {
           long category_id = Long.parseLong(request.getParameter("category_id"));
           subCategoria.setCategoria(categoriaService.getCategoriaById(category_id));
           subCategoriaService.saveSubCategoria(subCategoria);
       }catch (Error error){
           System.out.println("Subcategoria "+error);
       }
        return "redirect:/perfil";
    }

    @GetMapping("/editarSubCategoria/{idSubCategoria}")
    public String editarSubCategoria (@PathVariable("idSubCategoria") long idSubCategoria, @ModelAttribute("subcategoria") SubCategoria subcategoria, @ModelAttribute("categoria") Categoria categoria, @ModelAttribute("usuario") Usuario usuario, Model model, BindingResult bindingResult){

        subcategoria = subCategoriaService.getSubCategoriaById(idSubCategoria);
        List<SubCategoria> listaSubcategorias = subCategoriaService.getAllSubCategorias();
        List<Categoria> listaCategoria = categoriaService.getAllCategorias();

        System.out.println("Sub "+idSubCategoria);
        System.out.println("nombre SUb "+subcategoria.getCategoria().getIdCategoria());

        model.addAttribute("listaCategoria",listaCategoria);
        model.addAttribute("listaSubcategoria",listaSubcategorias);
        model.addAttribute("subcategoria",subcategoria);
        return "views/perfil/perfil";
    }

    @GetMapping("/eliminarSubcategoria/{idSubCategoria}")
    public String eliminarSubCategoria (@PathVariable("idSubCategoria") long idSubCategoria){

        try{
            subCategoriaService.deleteSubCategoriaById(idSubCategoria);
        }catch (Error e){
            System.out.println("Subcategoria controller "+e);
        }

        return "redirect:/perfil";
    }

    @PostMapping("editarPerfil")
    public String editarPerfil (Usuario usuario, Model model, WebRequest request){

        String password = request.getParameter("passwordInput") ;
        if (!password.isEmpty()){
            usuario.setPassword(password);
            usuarioService.saveUsuarioPerfil(usuario);
        }else {
            usuarioService.savePerfil(usuario);
        }
        return "redirect:/perfil";
    }


}

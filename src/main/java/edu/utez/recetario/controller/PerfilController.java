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
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.exceptions.TemplateInputException;

import javax.crypto.Cipher;
import javax.validation.Valid;
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

    private String mensaje;

    @GetMapping("/perfil")
    public String configuracion(@ModelAttribute("categoria") Categoria categoria,
                                @ModelAttribute("subcategoria") SubCategoria subcategoria,
                                @ModelAttribute("usuario")Usuario usuario,
                                Model model,
                                BindingResult bindingResult) {

        try {
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


            model.addAttribute("tabPerfil","tab-pane fade show active");
            model.addAttribute("tabCategoria","tab-pane fade");
            model.addAttribute("tabSubcategoria","tab-pane fade");

            model.addAttribute("tabSelectPerfil","nav-link active");
            model.addAttribute("tabSelectCategoria","nav-link");
            model.addAttribute("tabSelectSubcategoria","nav-link");

            return "views/perfil/perfil";
        }catch (Exception e){
            mensaje = usuarioService.codigosError(e.toString());
            System.out.println("Error en el controller de Perfil -> categoria"+mensaje);
            model.addAttribute("mensaje",mensaje);
            return "error/404";
        }
    }

    @PostMapping("/crearCategoria")
    public String crearCategoria (Model model, @Valid Categoria categoria, RedirectAttributes ra, Errors errors){

        try {
            if(errors.hasErrors()){
                ra.addFlashAttribute("error","ocurrio un error");
                return "redirect:/crearCategoria";
            }
            ra.addFlashAttribute("exito","registró");
            categoriaService.saveCategoria(categoria);
            return "redirect:/perfil";
        }catch (Error e){
            mensaje = usuarioService.codigosError(e.toString());
            System.out.println("Error en el controller de Perfil -> crearCategoria"+mensaje);
            model.addAttribute("mensaje",mensaje);
            return "error/404";
        }

    }

    @GetMapping("/editarCategoria/{idCategoria}")
    public String editarCategoria (@PathVariable("idCategoria") long idCategoria, @ModelAttribute("categoria") Categoria categoria, @ModelAttribute("subcategoria") SubCategoria subcategoria, @ModelAttribute("usuario")Usuario usuario, Model model) {

        try {
            categoria = categoriaService.getCategoriaById(idCategoria);
            List<Categoria> listaCategoria = categoriaService.getAllCategorias();
            List<SubCategoria> listaSubcategorias = subCategoriaService.getAllSubCategorias();

            model.addAttribute("listaSubcategoria", listaSubcategorias);
            model.addAttribute("listaCategoria",listaCategoria);
            model.addAttribute("categoria",categoria);

            model.addAttribute("tabPerfil","tab-pane fade");
            model.addAttribute("tabCategoria","tab-pane fade show active");
            model.addAttribute("tabSubcategoria","tab-pane fade");

            model.addAttribute("tabSelectPerfil","nav-link");
            model.addAttribute("tabSelectCategoria","nav-link active");
            model.addAttribute("tabSelectSubcategoria","nav-link");

            return "views/perfil/perfil";
        }catch (Exception e){
            mensaje = usuarioService.codigosError(e.toString());
            System.out.println("Error en el controller de Perfil -> editarCategoria"+mensaje);
            model.addAttribute("mensaje",mensaje);
            return "error/404";
        }
    }

    @GetMapping("/eliminarCategoria/{idCategoria}")
    public String eliminarCategoria (@PathVariable("idCategoria") long idCategoria, RedirectAttributes ra,Model model){

        try{
            ra.addFlashAttribute("exitoEliminar","Se elimino");
            categoriaService.deleteCategoriaById(idCategoria);
            return "redirect:/perfil";
        }catch (java.lang.Error e){
            mensaje = usuarioService.codigosError(e.toString());
            System.out.println("Error en el controller de Perfil -> eliminarCategoria"+mensaje);
            model.addAttribute("mensaje",mensaje);
            return "error/404";
        }


    }

    @PostMapping("/crearSubcategoria")
    public String crearSubcategoria ( @Valid SubCategoria subCategoria,
                                      RedirectAttributes ra,
                                      Model model,
                                      WebRequest request,
                                      Errors errors){
        if(errors.hasErrors()){
            System.out.println("Error de subcattegoria");
            ra.addFlashAttribute("error","hay un error");
            return "redirect:/perfil";
        }
        try {

            long category_id = Long.parseLong(request.getParameter("category_id"));
            if(category_id == 0){
                ra.addFlashAttribute("errorSub","hay un error");
                return "redirect:/perfil";
            }
            subCategoria.setCategoria(categoriaService.getCategoriaById(category_id));
            ra.addFlashAttribute("exito","todo bien");
            subCategoriaService.saveSubCategoria(subCategoria);
            return "redirect:/perfil";
       }catch (Exception e){
            mensaje = usuarioService.codigosError(e.toString());
            System.out.println("Error en el controller de Perfil -> crearSubcategoria"+mensaje);
            model.addAttribute("mensaje",mensaje);
            return "error/404";
       }
    }

    @GetMapping("/editarSubCategoria/{idSubCategoria}")
    public String editarSubCategoria (@PathVariable("idSubCategoria") long idSubCategoria, @ModelAttribute("subcategoria") SubCategoria subcategoria, @ModelAttribute("categoria") Categoria categoria, @ModelAttribute("usuario") Usuario usuario, Model model, BindingResult bindingResult){

        try {
            subcategoria = subCategoriaService.getSubCategoriaById(idSubCategoria);
            List<SubCategoria> listaSubcategorias = subCategoriaService.getAllSubCategorias();
            List<Categoria> listaCategoria = categoriaService.getAllCategorias();

            System.out.println("Sub "+idSubCategoria);
            System.out.println("nombre SUb "+subcategoria.getCategoria().getIdCategoria());

            model.addAttribute("listaCategoria",listaCategoria);
            model.addAttribute("listaSubcategoria",listaSubcategorias);
            model.addAttribute("subcategoria",subcategoria);

            model.addAttribute("tabPerfil","tab-pane fade");
            model.addAttribute("tabCategoria","tab-pane fade");
            model.addAttribute("tabSubcategoria","tab-pane fade show active");

            model.addAttribute("tabSelectPerfil","nav-link");
            model.addAttribute("tabSelectCategoria","nav-link");
            model.addAttribute("tabSelectSubcategoria","nav-link active");

            return "views/perfil/perfil";
        }catch (Exception e){
            mensaje = usuarioService.codigosError(e.toString());
            System.out.println("Error en el controller de Perfil -> editarSubcategoria"+mensaje);
            model.addAttribute("mensaje",mensaje);
            return "error/404";
        }
    }

    @GetMapping("/eliminarSubcategoria/{idSubCategoria}")
    public String eliminarSubCategoria (@PathVariable("idSubCategoria") long idSubCategoria,RedirectAttributes ra,Model model){

        try{
            ra.addFlashAttribute("exitoEliminar","Se elimino");
            subCategoriaService.deleteSubCategoriaById(idSubCategoria);
            return "redirect:/perfil";
        }catch (Error e){
            mensaje = usuarioService.codigosError(e.toString());
            System.out.println("Error en el controller de Perfil -> eliminarSubcategoria"+mensaje);
            model.addAttribute("mensaje",mensaje);
            return "error/404";
        }


    }

    @PostMapping("editarPerfil")
    public String editarPerfil (Model model,Usuario usuario , WebRequest request){

        try {
            String password = request.getParameter("passwordInput") ;
            if (!password.isEmpty()){
                usuario.setPassword(password);
                usuarioService.saveUsuarioPerfil(usuario);
            }else {
                usuarioService.savePerfil(usuario);
            }
            return "redirect:/perfil";
        }catch (Exception e) {
            mensaje = usuarioService.codigosError(e.toString());
            System.out.println("Error en el controller de Perfil -> editarPerfil" + mensaje);
            model.addAttribute("mensaje", mensaje);
            return "error/404";
        }
    }
}

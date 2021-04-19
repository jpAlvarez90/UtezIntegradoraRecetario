package edu.utez.recetario.controller;

import edu.utez.recetario.model.Bitacora;
import edu.utez.recetario.model.Categoria;
import edu.utez.recetario.model.SubCategoria;
import edu.utez.recetario.model.Usuario;
import edu.utez.recetario.service.BitacoraService;
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

    @Autowired
    BitacoraService bitacoraService;

    private String mensaje;

    @GetMapping("/perfil")
    public String configuracion(@ModelAttribute("categoria") Categoria categoria,
                                @ModelAttribute("subcategoria") SubCategoria subcategoria,
                                @ModelAttribute("usuario")Usuario usuario,
                                @ModelAttribute("bitacora") Bitacora bitacora,
                                Model model,
                                BindingResult bindingResult) {

        try {
            List<Categoria> listaCategoria = categoriaService.getAllCategorias();
            List<SubCategoria> listaSubcategorias = subCategoriaService.getAllSubCategorias();
            List<Bitacora> bitacoraList = bitacoraService.getAllBitacoraByOrderADesc(20);

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
            model.addAttribute("bitacora",bitacoraList);


            model.addAttribute("tabPerfil","tab-pane fade show active");
            model.addAttribute("tabCategoria","tab-pane fade");
            model.addAttribute("tabSubcategoria","tab-pane fade");
            model.addAttribute("tabBitacora","tab-pane fade");

            model.addAttribute("tabSelectPerfil","nav-link active");
            model.addAttribute("tabSelectCategoria","nav-link");
            model.addAttribute("tabSelectSubcategoria","nav-link");
            model.addAttribute("tabSelectBitacora","nav-link");

            return "views/perfil/perfil";
        }catch (Exception e){
            mensaje = usuarioService.codigosError(e.toString());
            model.addAttribute("mensaje",mensaje);
            return "error/error";
        }
    }

    @PostMapping("/crearCategoria")
    public String crearCategoria (Model model, @Valid Categoria categoria, Bitacora bitacora, RedirectAttributes ra, Errors errors){

        try {
            if(errors.hasErrors()){
                ra.addFlashAttribute("error","ocurrio un error");
                return "redirect:/crearCategoria";
            }
            ra.addFlashAttribute("exito","registró");
            if (categoria.getIdCategoria() != null){
                bitacora.setOperacion("Editar Categoria - "+categoria.getNombre());
            }else{
                bitacora.setOperacion("Insertar Categoria - "+categoria.getNombre());
            }
            categoriaService.saveCategoria(categoria);
            bitacora.setTabla("Categoria");
            bitacoraService.saveBitacora(bitacora);
            return "redirect:/perfil";
        }catch (Error e){
            mensaje = usuarioService.codigosError(e.toString());
            model.addAttribute("mensaje",mensaje);
            return "error/error";
        }

    }

    @GetMapping("/editarCategoria/{idCategoria}")
    public String editarCategoria (@PathVariable("idCategoria") long idCategoria, @ModelAttribute("categoria") Categoria categoria, @ModelAttribute("subcategoria") SubCategoria subcategoria, @ModelAttribute("usuario")Usuario usuario,@ModelAttribute("bitacora") Bitacora bitacora, Model model) {

        try {
            categoria = categoriaService.getCategoriaById(idCategoria);
            List<Bitacora> bitacoraList = bitacoraService.getAllBitacoraByOrderADesc(20);
            List<Categoria> listaCategoria = categoriaService.getAllCategorias();
            List<SubCategoria> listaSubcategorias = subCategoriaService.getAllSubCategorias();

            model.addAttribute("listaSubcategoria", listaSubcategorias);
            model.addAttribute("listaCategoria",listaCategoria);
            model.addAttribute("bitacora",bitacoraList);
            model.addAttribute("categoria",categoria);

            model.addAttribute("tabPerfil","tab-pane fade");
            model.addAttribute("tabCategoria","tab-pane fade show active");
            model.addAttribute("tabSubcategoria","tab-pane fade");
            model.addAttribute("tabBitacora","tab-pane fade");


            model.addAttribute("tabSelectPerfil","nav-link");
            model.addAttribute("tabSelectCategoria","nav-link active");
            model.addAttribute("tabSelectSubcategoria","nav-link");
            model.addAttribute("tabSelectBitacora","nav-link");


            return "views/perfil/perfil";
        }catch (Exception e){
            mensaje = usuarioService.codigosError(e.toString());
            model.addAttribute("mensaje",mensaje);
            return "error/error";
        }
    }

    @GetMapping("/eliminarCategoria/{idCategoria}")
    public String eliminarCategoria (@PathVariable("idCategoria") long idCategoria,Bitacora bitacora, RedirectAttributes ra,Model model){

        try{
            try {
                categoriaService.deleteCategoriaById(idCategoria);
                ra.addFlashAttribute("exitoEliminar","Se elimino");
            } catch (Exception e) {
                ra.addFlashAttribute("impossible","impossible");
                return "redirect:/perfil";
            }
            bitacora.setTabla("Categoria");
            bitacora.setOperacion("Eliminar Categoria");
            bitacoraService.saveBitacora(bitacora);
            return "redirect:/perfil";
        }catch (Exception e){
            mensaje = usuarioService.codigosError(e.toString());
            model.addAttribute("mensaje",mensaje);
            return "error/error";
        }


    }

    @PostMapping("/crearSubcategoria")
    public String crearSubcategoria ( @Valid SubCategoria subCategoria,
                                      Bitacora bitacora,
                                      RedirectAttributes ra,
                                      Model model,
                                      WebRequest request,
                                      Errors errors){
        if(errors.hasErrors()){
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
            if (subCategoria.getIdSubCategoria() != null){
                bitacora.setOperacion("Editar Subcategoria - "+subCategoria.getNombre());
            }else{
                bitacora.setOperacion("Insertar Subcategoria - "+subCategoria.getNombre());
            }
            subCategoriaService.saveSubCategoria(subCategoria);
            bitacora.setTabla("Subcategoria");
            bitacoraService.saveBitacora(bitacora);
            return "redirect:/perfil";
       }catch (Exception e){
            mensaje = usuarioService.codigosError(e.toString());
            model.addAttribute("mensaje",mensaje);
            return "error/error";
       }
    }

    @GetMapping("/editarSubCategoria/{idSubCategoria}")
    public String editarSubCategoria (@PathVariable("idSubCategoria") long idSubCategoria, @ModelAttribute("subcategoria") SubCategoria subcategoria, @ModelAttribute("categoria") Categoria categoria, @ModelAttribute("usuario") Usuario usuario,@ModelAttribute("bitacora") Bitacora bitacora, Model model, BindingResult bindingResult){

        try {
            subcategoria = subCategoriaService.getSubCategoriaById(idSubCategoria);
            List<Bitacora> bitacoraList = bitacoraService.getAllBitacoraByOrderADesc(20);
            List<SubCategoria> listaSubcategorias = subCategoriaService.getAllSubCategorias();
            List<Categoria> listaCategoria = categoriaService.getAllCategorias();

            model.addAttribute("listaCategoria",listaCategoria);
            model.addAttribute("listaSubcategoria",listaSubcategorias);
            model.addAttribute("bitacora",bitacoraList);
            model.addAttribute("subcategoria",subcategoria);

            model.addAttribute("tabPerfil","tab-pane fade");
            model.addAttribute("tabCategoria","tab-pane fade");
            model.addAttribute("tabSubcategoria","tab-pane fade show active");
            model.addAttribute("tabBitacora","tab-pane fade");


            model.addAttribute("tabSelectPerfil","nav-link");
            model.addAttribute("tabSelectCategoria","nav-link");
            model.addAttribute("tabSelectSubcategoria","nav-link active");
            model.addAttribute("tabSelectBitacora","nav-link");


            return "views/perfil/perfil";
        }catch (Exception e){
            mensaje = usuarioService.codigosError(e.toString());
            model.addAttribute("mensaje",mensaje);
            return "error/error";
        }
    }

    @GetMapping("/eliminarSubcategoria/{idSubCategoria}")
    public String eliminarSubCategoria (@PathVariable("idSubCategoria") long idSubCategoria,Bitacora bitacora,RedirectAttributes ra,Model model){

        try{
            try {
                subCategoriaService.deleteSubCategoriaById(idSubCategoria);
                ra.addFlashAttribute("exitoEliminar","Se elimino");
            } catch (Exception e) {
                ra.addFlashAttribute("impossible","impossible");
                return "redirect:/perfil";
            }
            bitacora.setTabla("Subcategoria");
            bitacora.setOperacion("Eliminar Subcategoria");
            bitacoraService.saveBitacora(bitacora);
            return "redirect:/perfil";
        }catch (Error e){
            mensaje = usuarioService.codigosError(e.toString());
            model.addAttribute("mensaje",mensaje);
            return "error/error";
        }


    }

    @PostMapping("editarPerfil")
    public String editarPerfil (Model model,Usuario usuario , Bitacora bitacora , WebRequest request){

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
            model.addAttribute("mensaje", mensaje);
            return "error/error";
        }
    }
}

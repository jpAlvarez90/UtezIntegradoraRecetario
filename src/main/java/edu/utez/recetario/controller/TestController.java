package edu.utez.recetario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/test/formulario")
    public String formReceta(Model model){
        return "views/receta/formulario";
    }

    @GetMapping("/test/receta")
    public String verRecetas(Model model){
        return "views/receta/ver_receta";
    }

    @GetMapping("/test/recetas")
    public String recetas(Model model){
        return "views/receta/recetas";
    }

    @GetMapping("/test/recetarios")
    public String misRecetarios(Model model){
        return "views/recetario/recetarios";
    }

    @GetMapping("/test/perfil")
    public String configuracion(Model model){
        return "views/perfil/perfil";
    }

    @GetMapping("/test/recuperar")
    public String recuperarContra(){
        return "recuperar_contrasena";
    }
}

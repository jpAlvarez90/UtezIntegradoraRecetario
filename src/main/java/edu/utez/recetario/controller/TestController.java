package edu.utez.recetario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.xml.transform.sax.SAXResult;

@Controller
public class TestController {

    @GetMapping("/test/form-receta")
    public String formReceta(Model model){
        return "views/receta/form-receta";
    }

    @GetMapping("/test/verRecertas")
    public String verRecetas(Model model){
        return "views/receta/verReceta";
    }

    @GetMapping("/test/recetas")
    public String recetas(Model model){
        return "views/receta/recetas";
    }

    @GetMapping("/test/misRecetarios")
    public String misRecetarios(Model model){
        return "views/recetario/misRecetarios";
    }

    @GetMapping("/test/configuracion")
    public String configuracion(Model model){
        return "views/perfil/configuracion";
    }

    @GetMapping("/test/recuperarContra")
    public String recuperarContra(){
        return "/recuperarContra";
    }
}

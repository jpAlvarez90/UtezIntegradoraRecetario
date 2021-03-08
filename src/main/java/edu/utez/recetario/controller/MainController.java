package edu.utez.recetario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String main(Model model){
        return "index";
    }

    @GetMapping("/form-receta")
    public String formReceta(Model model){
        return "views/form-receta";
    }

    @GetMapping("/verRecertas")
    public String verRecetas(Model model){
        return "views/verReceta";
    }

    @GetMapping("/recetas")
    public String recetas(Model model){
        return "views/recetas";
    }


    @GetMapping("/misRecetarios")
    public String misRecetarios(Model model){
        return "views/misRecetarios";
    }

}

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

    @GetMapping("/test")
    public String test(Model model) {
        return "views/test";
    }
}

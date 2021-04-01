package edu.utez.recetario.controller;

import edu.utez.recetario.model.Receta;
import edu.utez.recetario.service.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class MainController {

    private RecetaService recetaService;

    @Autowired
    public MainController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    @GetMapping("/")
    public String main(Model model){
        List<Receta> listaRecetas= recetaService.getAllRecetas();
        model.addAttribute("listaRecetas",listaRecetas);
        return "index";
    }

    @GetMapping("/mejor-calificado")
    public String mejorCalificados() {

        return "index";
    }

    @GetMapping("/mas-buscados")
    public String masBuscados() {
        return "index";
    }

    @GetMapping("/{idCategoria}")
    public String porCategoria(@PathVariable("idCategoria") long idCategoria) {
        return "index";
    }



















}

package edu.utez.recetario.controller;

import edu.utez.recetario.service.EnvioEmail;
import edu.utez.recetario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

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

}

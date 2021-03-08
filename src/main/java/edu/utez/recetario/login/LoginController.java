package edu.utez.recetario.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "views/login";
    }

    @PostMapping("/login")
    public String auth() {
        return "views/login";
    }

}

package edu.utez.recetario.controller;

import edu.utez.recetario.model.Receta;
import edu.utez.recetario.model.Usuario;
import edu.utez.recetario.service.EnvioEmail;
import edu.utez.recetario.service.RecetaService;
import edu.utez.recetario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.swing.text.View;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Controller
public class RecuperarContraseña {
    @Autowired
    EnvioEmail envioEmail;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private RecetaService recetaService;

    private String mensaje;

    @GetMapping("/recuperarC")
    public String recuperarContra(){
        return "recuperar_contrasena";
    }

    public String enviarDatos(Usuario usuario, Model model){
        return "plantillaEmail";
    }

    @PostMapping("/enviarCorreo")
    public String sendEmail (@RequestParam("mail") String mail, Model model,final Locale locale) throws IOException, MessagingException {
        try {
            List<Receta> listaRecetas = recetaService.getAllRecetasByOrderADesc(10);
            model.addAttribute("listaRecetas", listaRecetas);

            if (!usuarioService.getUsuarioByCorreo(mail).isEmpty()){
                Usuario usuario = usuarioService.getUsuarioByCorreo(mail).get();

                final Context ctx = new Context(locale);
                ctx.setVariable("correo", usuario.getCorreo());
                enviarDatos(usuario, model);

                SimpleMailMessage email = new SimpleMailMessage();
                Context context = new Context();


                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
                String html = templateEngine.process("plantillaEmail", ctx);

                helper.setTo(mail);
                helper.setSubject("Recuperar Contraseña");
                helper.setText(html,true);
                mailSender.send(mimeMessage);
            }else {
                System.out.println("No hay coincidencias");
            }

            return "redirect:index";
        }catch (Exception e){
            mensaje = usuarioService.codigosError(e.toString());
            System.out.println("Error en el controller de RecuperarContraseña -> sendEmail"+mensaje);
            model.addAttribute("mensaje",mensaje);
            return "error/404";
        }
    }

    @GetMapping("/cambioContrasena/{correo}")
    public String cambioContrasena(@PathVariable("correo") String correo,Model model,@ModelAttribute("usuario") Usuario usuario){
        try {
            usuario = usuarioService.getUsuarioByCorreo(correo).get();
            model.addAttribute("usuario", usuario);
            return "recuperar_contrasena_2";
        }catch (Exception e){
            mensaje = usuarioService.codigosError(e.toString());
            System.out.println("Error en el controller de RecuperarContraseña -> cambioContrasena "+mensaje);
            model.addAttribute("mensaje",mensaje);
            return "error/404";
        }
    }

    @PostMapping("/cambiarContrasena")
    public String cambiarContrasena (Model model,Usuario usuario, WebRequest request){
       try {
           List<Receta> listaRecetas = recetaService.getAllRecetasByOrderADesc(10);
           model.addAttribute("listaRecetas", listaRecetas);

           String contraNueva = request.getParameter("contraNueva");
           String contraConfirmacion = request.getParameter("contraConfirmacion");
           String idUsuario = request.getParameter("idUsuario");
           usuario = usuarioService.getUsuarioById(Long.parseLong(idUsuario));

           if (contraNueva.equals(contraConfirmacion)){
               usuario.setPassword(contraNueva);
               usuarioService.saveUsuarioPerfil(usuario);
           }else{
               System.out.println("Son diferentes");
           }

           return "index";
       }catch (Exception e){
           mensaje = usuarioService.codigosError(e.toString());
           System.out.println("Error en el controller de RecuperarContraseña -> cambiarContrasena "+mensaje);
           model.addAttribute("mensaje",mensaje);
           return "error/404";
       }
    }

}

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

    @GetMapping("/recuperarC")
    public String recuperarContra(){
        return "recuperar_contrasena";
    }

    public String enviarDatos(Usuario usuario, Model model){


        //model.addAttribute("usuario",usuario);
        System.out.println("Desde enviarDatos "+usuario.getNombre());
        return "plantillaEmail";
    }

    @PostMapping("/enviarCorreo")
    public String sendEmail (@RequestParam("mail") String mail, Model model,final Locale locale) throws IOException, MessagingException {
        List<Receta> listaRecetas = recetaService.getAllRecetasByOrderADesc(10);
        model.addAttribute("listaRecetas", listaRecetas);
        Usuario usuario = usuarioService.getUsuarioByCorreo(mail).get();

        final Context ctx = new Context(locale);
        ctx.setVariable("correo", usuario.getCorreo());
        enviarDatos(usuario, model);

        if (!usuarioService.getUsuarioByCorreo(mail).isEmpty()){
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

        return "index";
    }

    @GetMapping("/cambioContrasena/{correo}")
    public String cambioContrasena(@PathVariable("correo") String correo,Model model,@ModelAttribute("usuario") Usuario usuario){
        usuario = usuarioService.getUsuarioByCorreo(correo).get();
        model.addAttribute("usuario", usuario);
        System.out.println("llegada 1 "+usuario.getNombre());
        return "recuperar_contrasena_2";
    }

    @PostMapping("/cambiarContrasena")
    public String cambiarContrasena (Model model,Usuario usuario, WebRequest request){
        List<Receta> listaRecetas = recetaService.getAllRecetasByOrderADesc(10);
        model.addAttribute("listaRecetas", listaRecetas);

        String contraNueva = request.getParameter("contraNueva");
        String contraConfirmacion = request.getParameter("contraConfirmacion");
        String idUsuario = request.getParameter("idUsuario");
        usuario = usuarioService.getUsuarioById(Long.parseLong(idUsuario));
        System.out.println("cambio Contra 1 "+contraNueva + "  cambio contra 2 "+contraConfirmacion);
        System.out.println("usuario cambio "+usuario.getCorreo()+" - "+usuario.getNombre());
            usuario.setPassword(contraNueva);
            usuarioService.saveUsuarioPerfil(usuario);

        return "index";
    }

}

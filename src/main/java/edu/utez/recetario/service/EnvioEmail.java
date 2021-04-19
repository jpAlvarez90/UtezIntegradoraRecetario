package edu.utez.recetario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EnvioEmail {

    @Autowired
    private JavaMailSender mailSender;

    private UsuarioService usuarioService;

    public void sendEmail(String to, String subject, String content) {

       try{
           SimpleMailMessage email = new SimpleMailMessage();

           email.setTo(to);
           email.setSubject(subject);
           email.setText(content);

           mailSender.send(email);
       }catch (Exception e){
           usuarioService.codigosError(e.toString());
       }
    }
}

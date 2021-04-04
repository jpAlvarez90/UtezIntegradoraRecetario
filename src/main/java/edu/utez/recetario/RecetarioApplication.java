package edu.utez.recetario;

import edu.utez.recetario.service.AlmacenamientoImagenesService;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import javax.annotation.Resource;

@SpringBootApplication
public class RecetarioApplication implements CommandLineRunner {

    @Resource
    AlmacenamientoImagenesService almacenamientoImagenesService;

    public static void main(String[] args) {
        SpringApplication.run(RecetarioApplication.class, args);
    }

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }

    @Bean
    public SpringSecurityDialect springSecurityDialect(){ return new SpringSecurityDialect();}

    @Override
    public void run(String... args) throws Exception {
        almacenamientoImagenesService.init();
    }
}

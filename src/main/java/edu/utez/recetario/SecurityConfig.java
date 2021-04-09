package edu.utez.recetario;

import edu.utez.recetario.service.DetallesUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DetallesUsuarioService detallesUsuarioService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**",
                        "/static/**",
                        "/css/**",
                        "/js/**",
                        "/img/**",
                        "/icons/**",
                        "/ckeditor/**",
                        "/tinymce/**",
                        "/ckeditor/**",
                        "/json/**");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/login", "/registrar-usuario", "/test/**", "/ver-receta/**","/obtener-comentarios","/recuperarC/**","/enviarCorreo","/cambioContrasena/**","/cambiarContrasena")
                .permitAll()
                .antMatchers("/**","/perfil/**")
                .hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                //.anyRequest()
                //    .authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/")
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .csrf().disable();

    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(detallesUsuarioService);

    }
}

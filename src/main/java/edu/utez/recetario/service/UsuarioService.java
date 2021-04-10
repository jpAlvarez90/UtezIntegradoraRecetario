package edu.utez.recetario.service;

import edu.utez.recetario.model.Usuario;
import edu.utez.recetario.repository.UsuarioRepository;
import edu.utez.recetario.serviceInterface.UsuarioInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UsuarioInterface {

    private UsuarioRepository usuarioRepository;

    private RolService rolService;

    private BCryptPasswordEncoder passwordEncoder;

    private UsuarioInterface userInterface;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, RolService rolService, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolService = rolService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        try {
            usuario.setRol(rolService.getRole((long)2));
            usuario.setFechaRegistro(new Date());
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            return usuarioRepository.save(usuario);
        }catch(Exception e) {
            codigosError(e.toString());
            return null;
        }
    }

    @Override
    public Usuario savePerfil(Usuario usuario) {
       try {
           if (usuario.getRol().getRol() == "ROLE_ADMIN" ){
               usuario.setRol(rolService.getRole((long)1));
           }else if (usuario.getRol().getRol() == "ROLE_USER"){
               usuario.setRol(rolService.getRole((long)2));
           }
           usuario.setFechaRegistro(new Date());
           usuario.setPassword(usuario.getPassword());

           return usuarioRepository.save(usuario);
       }catch (Exception e){
           codigosError(e.toString());
           return  null;
       }
    }

    @Override
    public Usuario saveUsuarioPerfil(Usuario usuario) {
        try {
            if (usuario.getRol().getRol() == "ROLE_ADMIN" ){
                usuario.setRol(rolService.getRole((long)1));
            }else if (usuario.getRol().getRol() == "ROLE_USER"){
                usuario.setRol(rolService.getRole((long)2));
            }
            usuario.setFechaRegistro(new Date());
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

            return usuarioRepository.save(usuario);
        }catch (Exception e){
            codigosError(e.toString());
            return null;
        }
    }

    @Override
    public Usuario getUsuarioById(long id) {
        try {
            Optional<Usuario> optional = usuarioRepository.findById(id);
            Usuario usuario = null;
            if(optional.isPresent()) {
                usuario = optional.get();
            } else {
                throw new RuntimeException("User not found with for id :: "+id);
            }
            return usuario;
        }catch (Exception e){
            codigosError(e.toString());
            return null;
        }
    }

    @Override
    public void deleteUsuarioById(long id) {
        try {
            usuarioRepository.deleteById(id);
        }catch (Exception e){
            codigosError(e.toString());
        }
    }


    @Override
    public Optional<Usuario> getUsuarioByUsername(String username) {
        try {
            return usuarioRepository.findByUsername(username);
        }catch (Exception e){
            codigosError(e.toString());
            return null;
        }
    }


    @Override
    public String codigosError(String errores) {
        String[][] codigosError = new String[21][2];

        if (errores.indexOf(":") != -1) {
            errores = errores.substring(0, errores.indexOf(":"));
        }

        System.out.println("Palabra error: "+errores);

        codigosError[0][0]="PDE-01";
        codigosError[0][1]="java.lang.ArithmeticException";

        codigosError[1][0]="PDE-02";
        codigosError[1][1]="java.lang.IndexOutOfBoundsException";

        codigosError[2][0]="PDE-03";
        codigosError[2][1]="java.lang.ClassCastException";

        codigosError[3][0]="PDE-04";
        codigosError[3][1]="java.lang.NegativeArraySizeException";

        codigosError[4][0]="PDE-05";
        codigosError[4][1]="java.lang.NullPointerException";

        codigosError[5][0]="PDE-06";
        codigosError[5][1]="java.lang.NumberFormatException";

        codigosError[6][0]="PDE-07";
        codigosError[6][1]="java.lang.StringIndexOutOfBounds";

        codigosError[7][0]="PDE-08";
        codigosError[7][1]="java.util.EmptyStackException";

        codigosError[8][0]="PDE-09";
        codigosError[8][1]="java.lang.SecurityException";

        codigosError[9][0]="PDE-10";
        codigosError[9][1]="java.lang.IllegalStateException";

        codigosError[10][0]="PDE-11";
        codigosError[10][1]="java.util.ConnectionNotFoundException";

        codigosError[11][0]="PDE-12";
        codigosError[11][1]="java.lang.ClassNotFoundException";

        codigosError[12][0]="PDE-13";
        codigosError[12][1]="java.lang.NoClassDefFoundError";

        codigosError[13][0]="PDE-14";
        codigosError[13][1]="java.lang.AssertionError";

        codigosError[14][0]="PDE-15";
        codigosError[14][1]="java.lang.IllegalAccessException";

        codigosError[15][0]="PDE-16";
        codigosError[15][1]="java.lang.OutOfMemoryError";

        codigosError[16][0]="PDE-17";
        codigosError[16][1]="java.lang.InstantiationException";

        codigosError[17][0]="PDE-18";
        codigosError[17][1]="java.lang.InterruptedException";

        codigosError[18][0]="PDE-19";
        codigosError[18][1]="java.lang.ArrayStoreException";

        codigosError[19][0]="PDE-20";
        codigosError[19][1]="org.springframework.jdbc.CannotGetJdbcConnectionException";

        codigosError[20][0]="PDE-21";
        codigosError[20][1]="Error en la base de datos";


        for(int i=0;i<21;i++) {
            if(codigosError[i][1].equals(errores)) {
                return codigosError[i][0];
            }
        }

        return "Error no localizado :c";
    }

    @Override
    public Optional<Usuario> getUsuarioByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }
}

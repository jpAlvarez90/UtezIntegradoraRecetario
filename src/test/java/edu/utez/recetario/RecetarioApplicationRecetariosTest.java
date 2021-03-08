package edu.utez.recetario;

import edu.utez.recetario.model.Recetario;
import edu.utez.recetario.model.Usuario;
import edu.utez.recetario.service.RecetarioService;
import edu.utez.recetario.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RecetarioApplicationRecetariosTest {
    @Autowired
    RecetarioService recetarioService;

    @Autowired
    UsuarioService usuarioService;

    @Test
    void consultaRecetario(){
        Recetario recetario = recetarioService.getRecetarioById((long)1);
        if (recetario != null){
            System.out.println("name "+recetario.getNombre());
            System.out.println("usuario "+recetario.getUsuario().getNombre());
        }
        assert (recetario.getNombre() != null);
    }

    @Test
    void registrarRecetario(){
        Recetario recetario = new Recetario();
        Usuario user = usuarioService.getUsuarioById((long)2);

        recetario.setNombre("RecetarioPao");
        recetario.setUsuario(user);

        Recetario respuesta = recetarioService.saveRecetario(recetario);
        System.out.println("recetarioRegistrado" +respuesta.getNombre());
        assert (respuesta.getIdRecetario() != null);

    }

    @Test
    void modificarRecetario(){

        Recetario recetario = recetarioService.getRecetarioById((long)4);

        recetario.setNombre("Recetario pao modificado");

        Recetario retorno = recetarioService.saveRecetario(recetario);

        System.out.println("recetarioNombre "+retorno.getNombre());

        assert (retorno.getIdRecetario() != null);
    }

    @Test
    void eliminarRecetario(){
        recetarioService.deleteRecetarioById((long)4);

        assert (recetarioService.getRecetarioById((long)4) == null);
    }

}

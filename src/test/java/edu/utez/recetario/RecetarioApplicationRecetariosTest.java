package edu.utez.recetario;

import edu.utez.recetario.model.Recetario;
import edu.utez.recetario.model.Usuario;
import edu.utez.recetario.service.RecetarioService;
import edu.utez.recetario.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class RecetarioApplicationRecetariosTest {

    @Autowired
    RecetarioService recetarioService;

    @Autowired
    UsuarioService usuarioService;

    @Test
    void getRecetasByUsuarioID(){
        Usuario usuario = usuarioService.getUsuarioById((long) 7);

        List<Recetario> recetarioList = recetarioService.getRecetariosByUserId(usuario);

        assert(recetarioList.isEmpty());
    }
}

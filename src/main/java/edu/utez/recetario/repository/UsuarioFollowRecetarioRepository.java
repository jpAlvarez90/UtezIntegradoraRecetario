package edu.utez.recetario.repository;

import edu.utez.recetario.model.Receta;
import edu.utez.recetario.model.Recetario;
import edu.utez.recetario.model.Usuario;
import edu.utez.recetario.model.UsuarioFollowRecetario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioFollowRecetarioRepository extends JpaRepository<UsuarioFollowRecetario, Long> {

    List<UsuarioFollowRecetario> findByRecetarioAndUsuario(Recetario recetario, Usuario usuario);

    List<UsuarioFollowRecetario> findAllByUsuario(Usuario usuario);

    UsuarioFollowRecetario findByUsuarioAndRecetario(Usuario usuario, Recetario recetario);

}

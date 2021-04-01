package edu.utez.recetario.repository;

import edu.utez.recetario.model.Recetario;
import edu.utez.recetario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetarioRepository extends JpaRepository<Recetario, Long> {

    List<Recetario> findAllByUsuario(Usuario usuario);

}

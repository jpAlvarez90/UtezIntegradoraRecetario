package edu.utez.recetario.repository;

import edu.utez.recetario.model.Comentario;
import edu.utez.recetario.model.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario,Long> {

    List<Comentario> getAllByReceta(Receta receta);

}

package edu.utez.recetario.repository;

import edu.utez.recetario.model.Receta;
import edu.utez.recetario.model.Recetario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {

    List<Receta> findAllByRecetario(Recetario recetario);

}

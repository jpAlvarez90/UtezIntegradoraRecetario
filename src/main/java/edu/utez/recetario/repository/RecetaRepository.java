package edu.utez.recetario.repository;

import edu.utez.recetario.model.Receta;
import edu.utez.recetario.model.Recetario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {

    List<Receta> findAllByRecetario(Recetario recetario);

    @Query(value = "SELECT * FROM recetario_v1.receta order by id_receta desc limit :limit", nativeQuery = true)
    List<Receta> findByOrderByIdRecetaDesc(@Param("limit")int limit);

}

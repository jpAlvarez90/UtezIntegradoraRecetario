package edu.utez.recetario.repository;

import edu.utez.recetario.model.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {

    @Query(value = "select id_calificacion, receta,  avg(calificacion) as calificacion, usuario from calificacion group by receta order by calificacion desc", nativeQuery = true)
    public List<Calificacion> getRecetasByCalificacion();

}

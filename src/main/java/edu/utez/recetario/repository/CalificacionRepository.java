package edu.utez.recetario.repository;

import edu.utez.recetario.model.Calificacion;
import edu.utez.recetario.model.Receta;
import edu.utez.recetario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {

    @Query(value = "select id_calificacion, receta,  avg(calificacion) as calificacion, usuario from calificacion group by receta order by calificacion desc", nativeQuery = true)
    public List<Calificacion> getRecetasByCalificacion();

    Optional<Calificacion> findByRecetaAndUsuario(Receta receta, Usuario usuario);

}

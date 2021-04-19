package edu.utez.recetario.repository;

import edu.utez.recetario.model.Bitacora;
import edu.utez.recetario.model.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BitacoraRepository extends JpaRepository<Bitacora , Long> {

    @Query(value = "SELECT * FROM recetario_v1.bitacora order by id_bitacora desc limit :limit", nativeQuery = true)
    List<Bitacora> findByOrderByIdBitacoraDesc(@Param("limit")int limit);
}

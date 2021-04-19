package edu.utez.recetario.repository;

import edu.utez.recetario.model.Bitacora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BitacoraRepository extends JpaRepository<Bitacora , Long> {

}

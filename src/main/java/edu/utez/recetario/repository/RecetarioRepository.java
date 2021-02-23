package edu.utez.recetario.repository;

import edu.utez.recetario.model.Recetario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecetarioRepository extends JpaRepository<Recetario, Long> {
}

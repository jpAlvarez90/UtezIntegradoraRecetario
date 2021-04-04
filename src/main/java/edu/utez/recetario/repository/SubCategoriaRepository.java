package edu.utez.recetario.repository;

import edu.utez.recetario.model.Categoria;
import edu.utez.recetario.model.SubCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoriaRepository extends JpaRepository<SubCategoria, Long> {

    List<SubCategoria> findAllByCategoria(Categoria categoria);

}

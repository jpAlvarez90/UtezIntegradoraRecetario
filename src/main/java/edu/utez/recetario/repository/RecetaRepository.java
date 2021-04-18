package edu.utez.recetario.repository;

import edu.utez.recetario.model.Categoria;
import edu.utez.recetario.model.Receta;
import edu.utez.recetario.model.Recetario;
import edu.utez.recetario.model.SubCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {

    List<Receta> findAllByRecetario(Recetario recetario);

    List<Receta> findAllByCategoria(Categoria categoria);

    List<Receta> findAllByCategoriaAndSubCategoria(Categoria categoria, SubCategoria subCategoria);

    @Modifying
    @Transactional
    @Query(value = "UPDATE recetario_v1.receta SET vistas = :vistas WHERE (id_receta = :idReceta);", nativeQuery = true)
    void saveVistasRecetas(@Param("idReceta") long idReceta, @Param("vistas") int vistas);

    @Query(value = "SELECT * FROM recetario_v1.receta order by id_receta desc limit :limit", nativeQuery = true)
    List<Receta> findByOrderByIdRecetaDesc(@Param("limit")int limit);

    @Query(value = "SELECT * FROM recetario_v1.receta order by vistas desc limit :limit", nativeQuery = true)
    List<Receta> findByOrderByVistasDesc(@Param("limit")int limit);

    @Query(value = "SELECT r.id_receta, r.recetario, r.categoria, r.subcategoria, " +
            "r.titulo, r.descripcion, r.ingredientes, r.pasos, r.imagenes, r.fecha_publicacion, r.vistas " +
            "FROM recetario_v1.receta r\n" +
            "INNER JOIN recetario re ON r.recetario = re.id_recetario\n" +
            "INNER JOIN usuario u ON re.usuario = u.id_usuario\n" +
            "WHERE u.id_usuario = :idUsuario\n" +
            "ORDER BY r.id_receta\n" +
            "DESC\n" +
            "LIMIT :limit", nativeQuery = true)
    List<Receta> findLastRecetasByUsuario(@Param("idUsuario")long idUsuario, @Param("limit")int limit);













}

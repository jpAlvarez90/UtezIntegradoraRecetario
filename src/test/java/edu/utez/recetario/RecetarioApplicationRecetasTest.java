package edu.utez.recetario;

import edu.utez.recetario.model.*;
import edu.utez.recetario.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class RecetarioApplicationRecetasTest {

    @Autowired
    private RecetaService recetaService;

    @Autowired
    private RecetarioService recetarioService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private SubCategoriaService subCategoriaService;

    @Autowired
    private CalificacionService calificacionService;

    @Test
    void consultarRecetas() {
        List<Receta> recetas = recetaService.getAllRecetas();

        for (Receta receta: recetas) {
            System.out.println("Id receta: "+ receta.getIdReceta());
            System.out.println("Recetario: "+ receta.getRecetario().getNombre());
            System.out.println("Categoria: "+ receta.getCategoria().getNombre());
            System.out.println("Subcategoria: "+ receta.getSubCategoria().getNombre());
            System.out.println("Titulo: "+ receta.getTitulo());
            System.out.println("Descripcion: "+ receta.getDescripcion());
            System.out.println("Ingredientes: "+ receta.getIngredientes());
            System.out.println("Pasos: "+ receta.getPasos());
            System.out.println("Imagenes: "+ receta.getImagenes());
            System.out.println("Fecha de Publicacion: "+ receta.getFechaPublicacion());
        }

        assert (!recetas.isEmpty());
    }

    @Test
    void consultarRecetaId() {

        Receta receta = recetaService.getRecetaById((long) 1);

        if(receta == null) assert(false);

        assert (receta != null);

    }

    @Test
    void registrarRecetas() {

        Recetario recetario = recetarioService.getRecetarioById((long) 2);
        Categoria categoria = categoriaService.getCategoriaById((long) 1);
        SubCategoria subCategoria = subCategoriaService.getSubCategoriaById((long) 1);

        Receta receta = new Receta();

        receta.setRecetario(recetario);
        receta.setCategoria(categoria);
        receta.setSubCategoria(subCategoria);

        receta.setTitulo("");
        receta.setDescripcion("");
        receta.setIngredientes("");
        receta.setPasos("");
        receta.setImagenes("");
        receta.setFechaPublicacion(new Date());

        Receta retorno = recetaService.saveReceta(receta);

        assert(retorno != null);
    }

    @Test
    void getRecetasByCalificaciones() {
        List<Calificacion> recetas_calificaciones = calificacionService.getRecetasByCalificaciones();
        for (Calificacion calificacion: recetas_calificaciones) {
            System.out.println("ID receta: "+calificacion.getReceta().getIdReceta());
            System.out.println("Calificacion (Promediada): "+calificacion.getCalificacion());
        }
        assert(!recetas_calificaciones.isEmpty());
    }






















}

package edu.utez.recetario.serviceInterface;

import edu.utez.recetario.model.Calificacion;

import java.util.List;

public interface CalificacionInterface {

    List<Calificacion> getAllCalificaciones();

    Calificacion saveCalificacion(Calificacion calificacion);

    Calificacion getCalificacionById(long id);

    void deleteCalificacionById(long id);

    List<Calificacion> getRecetasByCalificaciones();

}

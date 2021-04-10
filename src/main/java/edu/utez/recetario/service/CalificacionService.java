package edu.utez.recetario.service;

import edu.utez.recetario.model.Calificacion;
import edu.utez.recetario.model.Receta;
import edu.utez.recetario.model.Usuario;
import edu.utez.recetario.repository.CalificacionRepository;
import edu.utez.recetario.serviceInterface.CalificacionInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CalificacionService implements CalificacionInterface {

    @Autowired
    private CalificacionRepository calificacionRepository;

    @Override
    public List<Calificacion> getAllCalificaciones() {
        return calificacionRepository.findAll();
    }

    @Override
    public Calificacion saveCalificacion(Calificacion calificacion) {
        return calificacionRepository.save(calificacion);
    }

    @Override
    public Calificacion getCalificacionById(long id) {
        Optional<Calificacion> optional = calificacionRepository.findById(id);
        Calificacion calificacion = null;
        if (optional.isPresent()) {
            calificacion = optional.get();
        } else {
            throw new RuntimeException("Calificacion not found for id :: "+id);
        }
        return calificacion;
    }

    @Override
    public void deleteCalificacionById(long id) {
        calificacionRepository.deleteById(id);
    }

    @Override
    public List<Calificacion> getRecetasByCalificaciones() {
        return calificacionRepository.getRecetasByCalificacion();
    }

    @Override
    public boolean existCalificacionByRecetaAndUsuario(Receta receta, Usuario usuario) {
        Optional<Calificacion> calificacion = calificacionRepository.findByRecetaAndUsuario(receta,usuario);
        return calificacion.isPresent();
    }

}

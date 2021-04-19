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

    private UsuarioService usuarioService;

    @Override
    public List<Calificacion> getAllCalificaciones() {
        try {
            return calificacionRepository.findAll();
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
            return null;
        }
    }

    @Override
    public Calificacion saveCalificacion(Calificacion calificacion) {
        try {
            return calificacionRepository.save(calificacion);
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
            return null;
        }
    }

    @Override
    public Calificacion getCalificacionById(long id) {
        try {
            Optional<Calificacion> optional = calificacionRepository.findById(id);
            Calificacion calificacion = null;
            if (optional.isPresent()) {
                calificacion = optional.get();
            } else {
                throw new RuntimeException("Calificacion not found for id :: "+id);
            }
            return calificacion;
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
            return null;
        }
    }

    @Override
    public void deleteCalificacionById(long id) {
        try {
            calificacionRepository.deleteById(id);
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
        }
    }

    @Override
    public List<Calificacion> getRecetasByCalificaciones() {
        try {
            return calificacionRepository.getRecetasByCalificacion();
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
            return null;
        }
    }

    @Override
    public boolean existCalificacionByRecetaAndUsuario(Receta receta, Usuario usuario) {
        try {
            Optional<Calificacion> calificacion = calificacionRepository.findByRecetaAndUsuario(receta,usuario);
            return calificacion.isPresent();
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
            return false;
        }

    }

}

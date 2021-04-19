package edu.utez.recetario.service;

import edu.utez.recetario.model.Comentario;
import edu.utez.recetario.model.Receta;
import edu.utez.recetario.repository.ComentarioRepository;
import edu.utez.recetario.serviceInterface.ComentarioInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComentarioService implements ComentarioInterface {

    @Autowired
    private ComentarioRepository comentarioRepository;

    private UsuarioService usuarioService;

    @Override
    public List<Comentario> getAllComentarios() {
        try{
            return comentarioRepository.findAll();
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
            return null;
        }
    }

    @Override
    public Comentario saveComentario(Comentario comentario) {
        try{
            return comentarioRepository.save(comentario);
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
            return null;
        }
    }

    @Override
    public Comentario getComentarioById(long id) {
        try{
            Optional<Comentario> optional = comentarioRepository.findById(id);
            Comentario comentario = null;
            if (optional.isPresent()) {
                comentario = optional.get();
            } else {
                throw new RuntimeException("Comentario not found for id :: "+id);
            }
            return comentario;
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
            return null;
        }

    }

    @Override
    public void deleteComentarioById(long id) {
        try{
            comentarioRepository.deleteById(id);
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
        }
    }

    @Override
    public List<Comentario> getComentarioByReceta(Receta receta) {
        try{
            return comentarioRepository.getAllByReceta(receta);
        }catch (Exception e){
            usuarioService.codigosError(e.toString());
            return null;
        }
    }
}

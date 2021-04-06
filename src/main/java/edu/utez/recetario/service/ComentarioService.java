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

    @Override
    public List<Comentario> getAllComentarios() {
        return comentarioRepository.findAll();
    }

    @Override
    public Comentario saveComentario(Comentario comentario) {
        return comentarioRepository.save(comentario);
    }

    @Override
    public Comentario getComentarioById(long id) {
        Optional<Comentario> optional = comentarioRepository.findById(id);
        Comentario comentario = null;
        if (optional.isPresent()) {
            comentario = optional.get();
        } else {
            throw new RuntimeException("Comentario not found for id :: "+id);
        }
        return comentario;
    }

    @Override
    public void deleteComentarioById(long id) {
        comentarioRepository.deleteById(id);
    }

    @Override
    public List<Comentario> getComentarioByReceta(Receta receta) {
        return comentarioRepository.getAllByReceta(receta);
    }
}

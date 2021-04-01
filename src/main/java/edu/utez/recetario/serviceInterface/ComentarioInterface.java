package edu.utez.recetario.serviceInterface;

import edu.utez.recetario.model.Comentario;

import java.util.List;

public interface ComentarioInterface {

    List<Comentario> getAllComentarios();

    Comentario saveComentario(Comentario comentario);

    Comentario getComentarioById(long id);

    void deleteComentarioById(long id);

    List<Comentario> getComentarioByRecetaId(long id);

}

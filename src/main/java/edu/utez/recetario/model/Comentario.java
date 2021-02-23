package edu.utez.recetario.model;

import javax.persistence.*;

@Entity
@Table(name = "comentario")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idComentario")
    private Long idComentario;

    @Column(name = "receta")
    private Receta receta;

    @Column(name = "comentario")
    private String comentario;

    public Comentario() {
    }

    public Comentario(Receta receta, String comentario) {
        this.receta = receta;
        this.comentario = comentario;
    }

    public Long getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(Long idComentario) {
        this.idComentario = idComentario;
    }

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}

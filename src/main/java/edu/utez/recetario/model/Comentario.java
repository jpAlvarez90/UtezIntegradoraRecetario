package edu.utez.recetario.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "comentario")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idComentario", nullable = false)
    private Long idComentario;

    @ManyToOne
    @JoinColumn(name = "receta", nullable = false)
    @JsonIgnore
    private Receta receta;

    @ManyToOne
    @JoinColumn(name = "usuario", nullable = false)
    @JsonIgnore
    private Usuario usuario;

    @Column(name = "comentario", nullable = false, columnDefinition = "TEXT")
    private String comentario;

    public Comentario() {
    }

    public Comentario(Receta receta, Usuario usuario, String comentario) {
        this.receta = receta;
        this.usuario = usuario;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}

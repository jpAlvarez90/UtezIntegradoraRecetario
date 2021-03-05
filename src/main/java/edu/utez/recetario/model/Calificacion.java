package edu.utez.recetario.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "calificacion")
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCalificacion", nullable = false)
    private Long idCalificacion;

    @ManyToOne
    @JoinColumn(name = "receta", nullable = false)
    private Receta receta;

    @ManyToOne
    @JoinColumn(name = "usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "calificacion", nullable = false)
    private int calificacion;

    public Calificacion() {
    }

    public Calificacion(Receta receta, Usuario usuario, int calificacion) {
        this.receta = receta;
        this.usuario = usuario;
        this.calificacion = calificacion;
    }

    public Long getIdCalificacion() {
        return idCalificacion;
    }

    public void setIdCalificacion(Long idCalificacion) {
        this.idCalificacion = idCalificacion;
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

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }
}

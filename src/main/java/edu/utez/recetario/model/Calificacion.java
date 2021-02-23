package edu.utez.recetario.model;

import javax.persistence.*;

@Entity
@Table(name = "calificacion")
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCalificacion")
    private Long idCalificacion;

    @Column(name = "receta")
    private Receta receta;

    @Column(name = "calificacion")
    private int calificacion;

    public Calificacion() {
    }

    public Calificacion(Receta receta, int calificacion) {
        this.receta = receta;
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

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }
}

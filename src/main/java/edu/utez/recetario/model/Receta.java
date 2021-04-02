package edu.utez.recetario.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "receta")
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReceta", nullable = false)
    private Long idReceta;

    @ManyToOne
    @JoinColumn(name = "recetario", nullable = false)
    private Recetario recetario;

    @ManyToOne
    @JoinColumn(name = "categoria", nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "subcategoria")
    private SubCategoria subCategoria;

    @OneToMany(mappedBy = "receta")
    private Set<Comentario> comentario = new HashSet<>();

    @OneToMany(mappedBy = "receta")
    private Set<Calificacion> calificacion = new HashSet<>();

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "ingredientes", nullable = false, columnDefinition = "TEXT")
    private String ingredientes;

    @Column(name = "pasos", nullable = false, columnDefinition = "TEXT")
    private String pasos;

    @Column(name = "imagenes", nullable = false, columnDefinition = "TEXT")
    private String imagenes;

    @Column(name = "fechaPublicacion", nullable = false)
    private Date fechaPublicacion;

    @Column(name = "vistas", columnDefinition = "integer default 0")
    private int vistas;

    public Receta() {
    }

    public Receta(Recetario recetario, Categoria categoria, SubCategoria subCategoria, String titulo, String descripcion, String ingredientes, String pasos, String imagenes, Date fechaPublicacion) {
        this.recetario = recetario;
        this.categoria = categoria;
        this.subCategoria = subCategoria;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ingredientes = ingredientes;
        this.pasos = pasos;
        this.imagenes = imagenes;
        this.fechaPublicacion = fechaPublicacion;
    }

    public Long getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Long idReceta) {
        this.idReceta = idReceta;
    }

    public Recetario getRecetario() {
        return recetario;
    }

    public void setRecetario(Recetario recetario) {
        this.recetario = recetario;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public SubCategoria getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(SubCategoria subCategoria) {
        this.subCategoria = subCategoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getPasos() {
        return pasos;
    }

    public void setPasos(String pasos) {
        this.pasos = pasos;
    }

    public String getImagenes() {
        return imagenes;
    }

    public void setImagenes(String imagenes) {
        this.imagenes = imagenes;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
}

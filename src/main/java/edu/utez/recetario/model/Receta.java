package edu.utez.recetario.model;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
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

    @OneToMany(mappedBy = "receta", cascade = {CascadeType.REMOVE})
    private Set<Comentario> comentario = new HashSet<>();

    @OneToMany(mappedBy = "receta", cascade = {CascadeType.REMOVE})
    private Set<Calificacion> calificacion = new HashSet<>();

    @Column(name = "titulo", nullable = false)
    @Pattern(regexp = "[a-zA-Z][a-zA-ZÀ-ÿ\\s]*$",message = "Solo se permiten letras mayusculas y minusculas")
    private String titulo;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    @Pattern(regexp = "[a-zA-z]+([ '-,&ñ][a-zA-ZÀ-ÿ\\s\\.¿?!¡]+)*", message = "No se permiten caracteres especiales")
    private String descripcion;

    @Column(name = "ingredientes", nullable = false, columnDefinition = "TEXT")
    private String ingredientes;

    @Column(name = "pasos", nullable = false, columnDefinition = "TEXT")
    private String pasos;

    @Column(name = "imagenes", columnDefinition = "TEXT")
    private String imagenes;

    @Column(name = "fechaPublicacion")
    private Date fechaPublicacion;

    @Column(name = "vistas", columnDefinition = "integer default 0", nullable = false)
    private int vistas;

    public Receta() {
    }

    public Receta(Recetario recetario, Categoria categoria, SubCategoria subCategoria, Set<Comentario> comentario, Set<Calificacion> calificacion, String titulo, String descripcion, String ingredientes, String pasos, String imagenes, Date fechaPublicacion, int vistas) {
        this.recetario = recetario;
        this.categoria = categoria;
        this.subCategoria = subCategoria;
        this.comentario = comentario;
        this.calificacion = calificacion;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ingredientes = ingredientes;
        this.pasos = pasos;
        this.imagenes = imagenes;
        this.fechaPublicacion = fechaPublicacion;
        this.vistas = vistas;
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

    public Set<Comentario> getComentario() {
        return comentario;
    }

    public void setComentario(Set<Comentario> comentario) {
        this.comentario = comentario;
    }

    public Set<Calificacion> getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Set<Calificacion> calificacion) {
        this.calificacion = calificacion;
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

    public int getVistas() {
        return vistas;
    }

    public void setVistas(int vistas) {
        this.vistas = vistas;
    }
}

package edu.utez.recetario.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCategoria", nullable = false)
    private Long idCategoria;

    @Column(name = "nombre", nullable = false)
    @Pattern(regexp = "[a-zA-Z][a-zA-ZÀ-ÿ\\s]*$",message = "Solo se permiten letras mayusculas y minusculas")
    private String nombre;

    @OneToMany(mappedBy = "categoria")
    private Set<SubCategoria> subCategoria = new HashSet<>();

    @OneToMany(mappedBy = "categoria")
    private Set<Receta> receta = new HashSet<>();

    public Categoria() {
    }

    public Categoria(String nombre, Set<SubCategoria> subCategoria, Set<Receta> receta) {
        this.nombre = nombre;
        this.subCategoria = subCategoria;
        this.receta = receta;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<SubCategoria> getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(Set<SubCategoria> subCategoria) {
        this.subCategoria = subCategoria;
    }

    public Set<Receta> getReceta() {
        return receta;
    }

    public void setReceta(Set<Receta> receta) {
        this.receta = receta;
    }
}

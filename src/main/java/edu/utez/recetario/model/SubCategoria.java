package edu.utez.recetario.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "subcategoria")
public class SubCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSubCategoria", nullable = false)
    private Long idSubCategoria;

    @ManyToOne
    @JoinColumn(name = "categoria", nullable = false)
    @JsonIgnore
    private Categoria categoria;

    @OneToMany(mappedBy = "subCategoria")
    @JsonIgnore
    private Set<Receta> receta = new HashSet<>();

    @Column(name = "nombre", nullable = false)
    @Pattern(regexp = "[a-zA-Z][a-zA-ZÀ-ÿ\\s]*$",message = "Solo se permiten letras mayusculas y minusculas")
    private String nombre;

    public SubCategoria() {
    }

    public SubCategoria(Categoria categoria, Set<Receta> receta, String nombre) {
        this.categoria = categoria;
        this.receta = receta;
        this.nombre = nombre;
    }

    public Long getIdSubCategoria() {
        return idSubCategoria;
    }

    public void setIdSubCategoria(Long idSubCategoria) {
        this.idSubCategoria = idSubCategoria;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Set<Receta> getReceta() {
        return receta;
    }

    public void setReceta(Set<Receta> receta) {
        this.receta = receta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

package edu.utez.recetario.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "subcategoria")
public class SubCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSubCategoria")
    private Long idSubCategoria;

    @ManyToOne
    @JoinColumn(name = "idCategoria")
    private Categoria categoria;

    @OneToMany(mappedBy = "subCategoria")
    private Set<Receta> receta = new HashSet<>();

    @Column(name = "nombre")
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

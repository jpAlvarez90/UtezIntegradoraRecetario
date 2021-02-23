package edu.utez.recetario.model;

import javax.persistence.*;

@Entity
@Table(name = "subcategoria")
public class SubCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSubCategoria")
    private Long idSubCategoria;

    @Column(name = "categoria")
    private Categoria categoria;

    @Column(name = "nombre")
    private String nombre;

    public SubCategoria() {
    }

    public SubCategoria(Categoria categoria, String nombre) {
        this.categoria = categoria;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

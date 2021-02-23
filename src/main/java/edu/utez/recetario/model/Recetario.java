package edu.utez.recetario.model;

import javax.persistence.*;

@Entity
@Table(name = "recetario")
public class Recetario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRecetario")
    private Long idRecetario;

    @Column(name = "usuario")
    private Usuario usuario;

    @Column(name = "nombre")
    private String nombre;

    public Recetario() {
    }

    public Recetario(Usuario usuario, String nombre) {
        this.usuario = usuario;
        this.nombre = nombre;
    }

    public Long getIdRecetario() {
        return idRecetario;
    }

    public void setIdRecetario(Long idRecetario) {
        this.idRecetario = idRecetario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

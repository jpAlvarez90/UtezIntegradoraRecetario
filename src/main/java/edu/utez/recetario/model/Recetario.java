package edu.utez.recetario.model;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "recetario")
public class Recetario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRecetario", nullable = false)
    private Long idRecetario;

    @ManyToOne  //idUsuario
    @JoinColumn(name = "usuario", nullable = false)
    private Usuario usuario;

    /*
    @ManyToMany
    @JoinTable(
            name = "usuario_follow_recetario",
            joinColumns = { @JoinColumn(name = "recetario", nullable = false) },
            inverseJoinColumns = { @JoinColumn(name = "usuario", nullable = false) }
    )
    */
    @OneToMany(mappedBy = "recetario")
    private Set<UsuarioFollowRecetario> usuarioFollowRecetarios = new HashSet<>();

    @OneToMany(mappedBy = "recetario")
    private Set<Receta> receta = new HashSet<>();

    @Column(name = "nombre", nullable = false)
    @Pattern(regexp = "^[a-zA-Z][a-z\\s]*$", message = "El recetario solo puede llevar letras mayusculas y minusculas")
    private String nombre;

    public Recetario() {
    }

    public Recetario(Usuario usuario, Set<UsuarioFollowRecetario> usuarioFollowRecetarios, Set<Receta> receta, String nombre) {
        this.usuario = usuario;
        this.usuarioFollowRecetarios = usuarioFollowRecetarios;
        this.receta = receta;
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

    public Set<UsuarioFollowRecetario> getUsuarioFollowRecetarios() {
        return usuarioFollowRecetarios;
    }

    public void setUsuarioFollowRecetarios(Set<UsuarioFollowRecetario> usuarioFollowRecetarios) {
        this.usuarioFollowRecetarios = usuarioFollowRecetarios;
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

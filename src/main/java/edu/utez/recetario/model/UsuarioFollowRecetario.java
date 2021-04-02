package edu.utez.recetario.model;

import javax.persistence.*;

@Entity
@Table(name = "usuario_follow_recetario")
public class UsuarioFollowRecetario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recetario")
    private Recetario recetario;

    @ManyToOne
    @JoinColumn(name = "usuario")
    private Usuario usuario;

    public UsuarioFollowRecetario() {
    }

    public UsuarioFollowRecetario(Recetario recetario, Usuario usuario) {
        this.recetario = recetario;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recetario getRecetario() {
        return recetario;
    }

    public void setRecetario(Recetario recetario) {
        this.recetario = recetario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

package edu.utez.recetario.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario", nullable = false)
    private Long idUsuario;

    @ManyToOne
    @JoinColumn(name = "rol", nullable = false)
    private Rol rol;

    @OneToMany(mappedBy = "usuario")
    private Set<Recetario> recetario = new HashSet<>();

    @ManyToMany(mappedBy = "usuarios")
    private Set<Recetario> recetarios = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    private Set<Calificacion> calificacion = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    private Set<Comentario> comentario = new HashSet<>();

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "primerApellido", nullable = false)
    private String primerApellido;

    @Column(name = "segundoApellido", nullable = false)
    private String segundoApellido;

    @Column(name = "correo", nullable = false)
    private String correo;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "usuario", nullable = false)
    private String usuario;

    @Column(name = "fechaRegistro", nullable = false)
    private Date fechaRegistro;

    public Usuario() {
    }

    public Usuario(Rol rol, Set<Recetario> recetario, Set<Calificacion> calificacion, Set<Comentario> comentario, String nombre, String primerApellido, String segundoApellido, String correo, String password, String usuario, Date fechaRegistro) {
        this.rol = rol;
        this.recetario = recetario;
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.correo = correo;
        this.password = password;
        this.usuario = usuario;
        this.fechaRegistro = fechaRegistro;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Set<Recetario> getRecetario() {
        return recetario;
    }

    public void setRecetario(Set<Recetario> recetario) {
        this.recetario = recetario;
    }

    public Set<Calificacion> getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Set<Calificacion> calificacion) {
        this.calificacion = calificacion;
    }

    public Set<Comentario> getComentario() {
        return comentario;
    }

    public void setComentario(Set<Comentario> comentario) {
        this.comentario = comentario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}

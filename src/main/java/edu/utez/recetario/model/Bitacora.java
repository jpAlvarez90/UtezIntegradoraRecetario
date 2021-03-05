package edu.utez.recetario.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bitacora")
public class Bitacora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idBitacora", nullable = false)
    private Long idBitacora;

    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @Column(name = "usuario", nullable = false)
    private String usuario;

    @Column(name = "tabla", nullable = false)
    private String tabla;

    @Column(name = "operacion", nullable = false)
    private String operacion;

    public Bitacora() {
    }

    public Bitacora(Date fecha, String usuario, String tabla, String operacion) {
        this.fecha = fecha;
        this.usuario = usuario;
        this.tabla = tabla;
        this.operacion = operacion;
    }

    public Long getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(Long idBitacora) {
        this.idBitacora = idBitacora;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }
}

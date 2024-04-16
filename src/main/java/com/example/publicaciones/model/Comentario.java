package com.example.publicaciones.model;

import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "comentario")


public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comentario")
    private Long idComentario;
    @Column(name = "comentario")
    private String comentario;
    @Column(name = "usuario_comentario")
    private String usuario;
    @Column(name = "fecha_comentario")
    private Date fecha;
    @Column(name = "calificacion")
    private Double calificacion;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_publicacion_comentario")
    private Publicacion publicacionComment;
    
    public Comentario() { }
    public Comentario(Long idComentario, String comentario, String usuario, Date fecha, Double calificacion) {
        this.idComentario = idComentario;
        this.comentario = comentario;
        this.usuario = usuario;
        this.fecha = fecha;
        this.calificacion = calificacion;
    }

    public Long getIdComentario(){
        return idComentario;
    }
    public String getComentario(){
        return comentario;
    }

    public String getUsuario(){
        return usuario;
    }

    public Date getFecha(){
        return fecha;
    }

    public Double getCalificacion(){
        return calificacion;
    }
}

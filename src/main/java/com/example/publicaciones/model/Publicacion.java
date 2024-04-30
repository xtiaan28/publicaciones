package com.example.publicaciones.model;

import org.springframework.hateoas.RepresentationModel;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "publicacion")

public class Publicacion extends RepresentationModel<Publicacion>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_publicacion")
    private Long idPublicacion;

    @NotBlank(message = "No puede ingresar un titulo vacio")
    @Column(name= "titulo")
    private String titulo;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "usuario_publica")
    private String usuario;
    @Column(name = "fecha_publicacion")
    private Date fechaCreacion;
    @Column(name = "fecha_edicion")
    private Date fechaActualizacion;
    //@JoinTable(name = "comentario")
    @OneToMany(mappedBy = "publicacionComment")
    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "publicacion", cascade = CascadeType.ALL)
    private List<Comentario> comentarios;

    public Publicacion() { }
    public Publicacion(Long idPublicacion, String titulo, String tipo, String usuario, Date fechaCreacion, Date fechaActualizacion){
        this.idPublicacion = idPublicacion;
        this.titulo = titulo;
        this.tipo = tipo;
        this.usuario = usuario;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public Long getIdPublicacion(){
        return idPublicacion;
    }

    public String getTitulo(){
        return titulo;
    }

    public String getTipo(){
        return tipo;
    }

    public String getUsuario(){
        return usuario;
    }

    public Date getFechaCreacion(){
        return fechaCreacion;
    }

    public Date getFechaActualizacion(){
        return fechaActualizacion;
    }

    public List<Comentario> getComentarios(){
        return comentarios;
    }

    public void setIdPublicacion(Long idPublicacion) {
        this.idPublicacion = idPublicacion;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
    public void setComentarios(List<Comentario> comentarios) { 
        this.comentarios = comentarios; 
    }

    @Override
    public String toString() {
        return "Publicacion{" + "id=" + idPublicacion + ", titulo='" + titulo + '\'' + ", usuario='" + usuario + '\'' + '}';
    }
}

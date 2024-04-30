package com.example.publicaciones.controller;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;


import com.example.publicaciones.model.Comentario;
import com.example.publicaciones.model.Publicacion;

import com.example.publicaciones.service.PublicacionService;

@RestController
@RequestMapping("/publicaciones")
public class PublicacionController {
    private static final Logger log = LoggerFactory.getLogger(PublicacionController.class);
    @Autowired
    private PublicacionService publicacionService;

    @GetMapping
    public CollectionModel<EntityModel<Publicacion>> getAllPublicaciones() {
        List<Publicacion> publicaciones = publicacionService.getAllPublicaciones();
        log.info("GET /publicaciones");
        log.info("Retornando todos las publicaciones");
        List<EntityModel<Publicacion>> publicacionesResources = publicaciones.stream()
            .map( publicacion -> EntityModel.of(publicacion,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPublicacionById(publicacion.getIdPublicacion())).withSelfRel()
            ))
            .collect(Collectors.toList());

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllPublicaciones());
        CollectionModel<EntityModel<Publicacion>> resources = CollectionModel.of(publicacionesResources, linkTo.withRel("publicaciones"));

        return resources;
    }
    /*@GetMapping
    public List<Publicacion> getAllPublicaciones() {
        return publicacionService.getAllPublicaciones();
    }*/
    @GetMapping("/{id}")
    public EntityModel<Publicacion> getPublicacionById(@PathVariable Long id) {
        Optional<Publicacion> publicacion = publicacionService.getPublicacionById(id);

        if (publicacion.isPresent()) {
            return EntityModel.of(publicacion.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPublicacionById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllPublicaciones()).withRel("all-publicaciones"));
        } else {
            throw new PublicacionNotFoundException("Publicacion not found with id: " + id);
        }
    }
    /*@GetMapping("/{id}")
    public ResponseEntity <Object> getPublicacionById(@PathVariable Long id) {
        Optional<Publicacion> publicacion = publicacionService.getPublicacionById(id);
        if(publicacion.isEmpty()){
            log.error("No se encontro publicacion con ID {}", id);
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No se encontro publicacion con ID " + id));
            //return ResponseEntity.notFound().build();
        }
        log.info("Publicacion encontrada con exito");
        return ResponseEntity.ok(publicacionService.getPublicacionById(id));
    }*/
    
    @GetMapping("promedios/{id}")
    public ResponseEntity <Object> getPromedioByPublicacion(@PathVariable Long id){
        List<Publicacion> publis = publicacionService.getAllPublicaciones();
        Double promedio = 0.0;
        DecimalFormat df = new DecimalFormat("#.00");
        String resultado = "";
        for(Publicacion publicacion : publis){
            if(publicacion.getIdPublicacion() == id){
                List<Comentario> comentario = publicacion.getComentarios();
                if(comentario.isEmpty()){
                    //resultado = "No existen comentarios para la publicacion: "+ id;
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No se encontraron comentarios para publicacion con ID " + id));
                }
                Double suma = 0.0;
                for(Comentario comentarioTemp : comentario){   
                    suma = suma + comentarioTemp.getCalificacion();
                }
                promedio = suma / comentario.size();
                resultado = "Promedio: "+df.format(promedio);
                log.info("Promedio: "+df.format(promedio) );
            }
        }
        log.info("Publicacion encontrada con exito");
        return ResponseEntity.ok(resultado);

    }

    @PostMapping
    public EntityModel<Publicacion> createPublicacion(@Validated @RequestBody Publicacion publicacion) {
        Publicacion createdPublicacion = publicacionService.createPublicacion(publicacion);
            return EntityModel.of(createdPublicacion,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPublicacionById(createdPublicacion.getIdPublicacion())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllPublicaciones()).withRel("all-publicaciones"));

    }
    /*@PostMapping
    public ResponseEntity<Object> createPublicacion(@RequestBody Publicacion publicacion){
        Publicacion createdPublicacion = publicacionService.createPublicacion(publicacion);
        if(createdPublicacion == null){
            log.error("Error al crear publicacion {}",publicacion);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Error al crear la publicacion")); 
        }
        return ResponseEntity.ok(createdPublicacion);
        //return peliculaService.createPelicula(pelicula);
    }*/
    @PutMapping("/{id}")
    public EntityModel<Publicacion> updateVenta(@PathVariable Long id, @RequestBody Publicacion publicacion) {
        Publicacion updatedPublicacion = publicacionService.updatePublicacion(id, publicacion);
        return EntityModel.of(updatedPublicacion,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPublicacionById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllPublicaciones()).withRel("all-publicaciones"));

    }
    /*@PutMapping("/{id}")
    public ResponseEntity<Object> updatePublicacion(@PathVariable Long id, @RequestBody Publicacion publicacion) {
        Publicacion updatedPublicacion = publicacionService.updatePublicacion(id, publicacion);
        if(updatedPublicacion == null){
            log.error("Error al actualizar {}",publicacion);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Error al actualizar la publicacion id "+id)); 
        }
        return ResponseEntity.ok(updatedPublicacion);
    }*/
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePublicacion(@PathVariable Long id){
        publicacionService.deletePublicacion(id);
        return ResponseEntity.ok("ID "+id+ " eliminado correctamente");
    }
    static class ErrorResponse {
        private final String message;

        public ErrorResponse(String message){
            this.message = message;
        }

        public String getMessage(){
            return message;
        }
    }
}

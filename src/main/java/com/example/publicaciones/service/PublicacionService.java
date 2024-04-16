package com.example.publicaciones.service;

import java.util.List;
import java.util.Optional;

import com.example.publicaciones.model.Publicacion;

public interface PublicacionService {
    List<Publicacion> getAllPublicaciones();
    Optional<Publicacion> getPublicacionById(Long id);

    Publicacion createPublicacion(Publicacion publicacion);
    Publicacion updatePublicacion(Long id, Publicacion publicacion);
    void deletePublicacion(Long id);
}

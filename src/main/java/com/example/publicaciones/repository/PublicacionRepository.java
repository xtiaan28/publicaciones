package com.example.publicaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.publicaciones.model.Publicacion;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long>{
    
}

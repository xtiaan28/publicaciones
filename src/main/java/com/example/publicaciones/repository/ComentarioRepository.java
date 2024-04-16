package com.example.publicaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.publicaciones.model.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
}
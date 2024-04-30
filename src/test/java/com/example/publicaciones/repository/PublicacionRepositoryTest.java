package com.example.publicaciones.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.publicaciones.model.Publicacion;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class PublicacionRepositoryTest {
    @Autowired
    private PublicacionRepository publicacionRepository;

    @Test
    public void guardarPublicacionTest() {
        Publicacion publicacion = new Publicacion();
        publicacion.setTitulo("Titulo1");

        Publicacion resultado = publicacionRepository.save(publicacion);

        assertNotNull(resultado.getIdPublicacion());
        assertEquals("Titulo1", resultado.getTitulo());
    }
}

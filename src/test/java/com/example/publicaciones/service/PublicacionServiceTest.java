package com.example.publicaciones.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.publicaciones.model.Publicacion;
import com.example.publicaciones.repository.PublicacionRepository;


@ExtendWith(MockitoExtension.class)

public class PublicacionServiceTest {
    @InjectMocks
    private PublicacionServiceImpl publicacionServicio;

    @Mock
    private PublicacionRepository publicacionRepositoryMock;

    @Test
    public void guardarVentaTest() {

        Publicacion publicacion = new Publicacion();
        publicacion.setTitulo("Titulo1");

        when(publicacionRepositoryMock.save(any())).thenReturn(publicacion);

        Publicacion resultado = publicacionServicio.createPublicacion(publicacion);

        assertEquals("Titulo1", resultado.getTitulo());
    }
}

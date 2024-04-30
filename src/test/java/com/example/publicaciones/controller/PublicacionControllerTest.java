package com.example.publicaciones.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.web.servlet.MockMvc;

import com.example.publicaciones.model.Publicacion;
import com.example.publicaciones.service.PublicacionService;

@WebMvcTest(PublicacionControllerTest.class)
public class PublicacionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublicacionService publicacionServiceMock;

    @Test
    public void obtenerTodosTest() throws Exception {
        Publicacion publicacion1 = new Publicacion();
        publicacion1.setTitulo("Titulo1");
        publicacion1.setIdPublicacion(1L);
        Publicacion publicacion2 = new Publicacion();
        publicacion2.setTitulo("Titulo2");
        publicacion2.setIdPublicacion(2L);
        List<Publicacion> publicaciones = List.of(publicacion1, publicacion2);

        List<EntityModel<Publicacion>> publicacionesResources = publicaciones.stream()
            .map(publicacion -> EntityModel.of(publicacion))
            .collect(Collectors.toList());

        when(publicacionServiceMock.getAllPublicaciones()).thenReturn(publicaciones);

        mockMvc.perform(get("/ventas"))
                .andExpect(status().isOk())
                // Here, use direct JSON path matching without Matchers
                .andExpect(jsonPath("$._embedded.publicaciones.length()").value(2))
                .andExpect(jsonPath("$._embedded.publicaciones[0].titulo").value("Titulo1"))
                .andExpect(jsonPath("$._embedded.publicaciones[1].titulo").value("Titulo2"))
                .andExpect(jsonPath("$._embedded.publicaciones[0]._links.self.href").value("http://localhost:8080/publicaciones/1"))
                .andExpect(jsonPath("$._embedded.publicaciones[1]._links.self.href").value("http://localhost:8080/publicaciones/2"));
    }
}

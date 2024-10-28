package com.springboot.microservicio.growby.controller;

import com.springboot.microservicio.growby.dto.OrdenDTO;
import com.springboot.microservicio.growby.model.Orden;
import com.springboot.microservicio.growby.service.OrdenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrdenControllerTest {

    @InjectMocks
    private OrdenController ordenController;

    @Mock
    private OrdenService ordenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_ShouldReturnListOfOrdenes() {
        Orden orden1 = new Orden();
        orden1.setId(1L);
        Orden orden2 = new Orden();
        orden2.setId(2L);
        List<Orden> ordenes = Arrays.asList(orden1, orden2);
        when(ordenService.findAll()).thenReturn(ordenes);

        ResponseEntity<List<Orden>> response = ordenController.getAll();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(ordenes, response.getBody());
    }

    @Test
    void getOrdenesByClienteId_ShouldReturnListOfOrdenes() {
        Long clienteId = 1L;
        Orden orden1 = new Orden();
        orden1.setId(1L);
        Orden orden2 = new Orden();
        orden2.setId(2L);
        List<Orden> ordenes = Arrays.asList(orden1, orden2);
        when(ordenService.findOrdenesByClienteId(clienteId)).thenReturn(ordenes);

        ResponseEntity<List<Orden>> response = ordenController.getOrdenesByClienteId(clienteId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(ordenes, response.getBody());
    }

    @Test
    void createOrder_ShouldReturnCreatedOrden() {
        OrdenDTO ordenDTO = new OrdenDTO();
        Orden orden = new Orden();
        orden.setId(1L);
        when(ordenService.save(any(OrdenDTO.class))).thenReturn(orden);

        ResponseEntity<Orden> response = ordenController.createOrder(ordenDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(orden, response.getBody());
    }

    @Test
    void updateOrder_ShouldReturnUpdatedOrden() {
        Long id = 1L;
        OrdenDTO ordenDTO = new OrdenDTO();
        Orden orden = new Orden();
        orden.setId(id);
        when(ordenService.update(id, ordenDTO)).thenReturn(orden);

        ResponseEntity<Orden> response = ordenController.updateOrder(id, ordenDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(orden, response.getBody());
    }

    @Test
    void deleteOrder_ShouldReturnNoContent() {

        Long id = 1L;

        ResponseEntity<Void> response = ordenController.deleteOrder(id);

        assertEquals(204, response.getStatusCodeValue());
        verify(ordenService, times(1)).delete(id);
    }
}

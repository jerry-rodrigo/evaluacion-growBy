package com.springboot.microservicio.growby.controller;

import com.springboot.microservicio.growby.dto.ProductoDTO;
import com.springboot.microservicio.growby.model.Producto;
import com.springboot.microservicio.growby.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductoControllerTest {

    @InjectMocks
    private ProductoController productoController;

    @Mock
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProductos_ShouldReturnListOfProductos() {
        Producto producto1 = new Producto();
        producto1.setId(1L);
        Producto producto2 = new Producto();
        producto2.setId(2L);
        List<Producto> productos = Arrays.asList(producto1, producto2);
        when(productoService.findAll()).thenReturn(productos);

        ResponseEntity<List<Producto>> response = productoController.getAllProductos();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(productos, response.getBody());
    }

    @Test
    void getProductoById_ShouldReturnProductoIfExists() {
        Long id = 1L;
        Producto producto = new Producto();
        producto.setId(id);
        when(productoService.getProductoById(id)).thenReturn(Optional.of(producto));

        ResponseEntity<Producto> response = productoController.getProductoById(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(producto, response.getBody());
    }

    @Test
    void getProductoById_ShouldReturnNotFoundIfDoesNotExist() {
        Long id = 1L;
        when(productoService.getProductoById(id)).thenReturn(Optional.empty());

        ResponseEntity<Producto> response = productoController.getProductoById(id);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void createProducto_ShouldReturnCreatedProducto() {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setNombre("Nuevo Producto");
        Producto producto = new Producto();
        producto.setId(1L);
        when(productoService.save(any(ProductoDTO.class))).thenReturn(producto);

        ResponseEntity<Producto> response = productoController.createProducto(productoDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(producto, response.getBody());
    }

    @Test
    void updateProducto_ShouldReturnUpdatedProducto() {
        Long id = 1L;
        ProductoDTO productoDTO = new ProductoDTO();
        Producto producto = new Producto();
        producto.setId(id);
        when(productoService.update(id, productoDTO)).thenReturn(producto);

        ResponseEntity<Producto> response = productoController.updateProducto(id, productoDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(producto, response.getBody());
    }

    @Test
    void deleteProducto_ShouldReturnNoContent() {
        Long id = 1L;

        ResponseEntity<Void> response = productoController.deleteProducto(id);

        assertEquals(204, response.getStatusCodeValue());
        verify(productoService, times(1)).delete(id);
    }
}

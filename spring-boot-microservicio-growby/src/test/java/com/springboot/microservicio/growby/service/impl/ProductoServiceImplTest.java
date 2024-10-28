package com.springboot.microservicio.growby.service.impl;

import com.springboot.microservicio.growby.dto.ProductoDTO;
import com.springboot.microservicio.growby.exception.ProductoNotFoundException;
import com.springboot.microservicio.growby.model.Producto;
import com.springboot.microservicio.growby.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductoServiceImplTest {

    private ProductoServiceImpl productoService;
    private ProductoRepository productoRepository;

    @BeforeEach
    void setUp() {
        productoRepository = mock(ProductoRepository.class);
        productoService = new ProductoServiceImpl(productoRepository);
    }

    @Test
    void findAll_ShouldReturnAllProducts() {
        Producto producto1 = new Producto();
        producto1.setId(1L);
        producto1.setNombre("Producto 1");

        Producto producto2 = new Producto();
        producto2.setId(2L);
        producto2.setNombre("Producto 2");

        when(productoRepository.findAll()).thenReturn(List.of(producto1, producto2));

        List<Producto> productos = productoService.findAll();

        assertEquals(2, productos.size());
        assertEquals("Producto 1", productos.get(0).getNombre());
        assertEquals("Producto 2", productos.get(1).getNombre());
    }

    @Test
    void getProductoById_ShouldReturnProduct_WhenExists() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto 1");

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Optional<Producto> result = productoService.getProductoById(1L);

        assertTrue(result.isPresent());
        assertEquals("Producto 1", result.get().getNombre());
    }

    @Test
    void getProductoById_ShouldReturnEmpty_WhenDoesNotExist() {
        when(productoRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Producto> result = productoService.getProductoById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void save_ShouldReturnSavedProduct() {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setNombre("Nuevo Producto");
        productoDTO.setDescripcion("Descripción del producto");
        productoDTO.setPrecio(BigDecimal.valueOf(100.0));
        productoDTO.setEstado("ACTIVO");

        Producto savedProducto = new Producto();
        savedProducto.setId(1L);
        savedProducto.setNombre("Nuevo Producto");
        savedProducto.setFechaCreacion(new Date());

        when(productoRepository.save(any(Producto.class))).thenReturn(savedProducto);

        Producto result = productoService.save(productoDTO);

        assertEquals("Nuevo Producto", result.getNombre());
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void update_ShouldThrowException_WhenNotFound() {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setNombre("Producto Actualizado");

        when(productoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductoNotFoundException.class, () -> productoService.update(1L, productoDTO));
    }

    @Test
    void delete_ShouldCallDeleteById() {
        productoService.delete(1L);

        verify(productoRepository, times(1)).deleteById(1L);
    }
}

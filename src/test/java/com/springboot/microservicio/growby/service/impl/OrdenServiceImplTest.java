package com.springboot.microservicio.growby.service.impl;

import com.springboot.microservicio.growby.dto.OrdenDTO;
import com.springboot.microservicio.growby.exception.OrdenNotFoundException;
import com.springboot.microservicio.growby.model.Cliente;
import com.springboot.microservicio.growby.model.Orden;
import com.springboot.microservicio.growby.model.Producto;
import com.springboot.microservicio.growby.repository.ClienteRepository;
import com.springboot.microservicio.growby.repository.OrdenRepository;
import com.springboot.microservicio.growby.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrdenServiceImplTest {

    @Mock
    private OrdenRepository ordenRepository;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private OrdenServiceImpl ordenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_ShouldReturnListOfOrdenes() {
        List<Orden> expectedOrdenes = new ArrayList<>();
        expectedOrdenes.add(new Orden());
        when(ordenRepository.findAll()).thenReturn(expectedOrdenes);

        List<Orden> actualOrdenes = ordenService.findAll();

        assertEquals(expectedOrdenes, actualOrdenes);
    }

    @Test
    void findOrdenesByClienteId_ShouldReturnListOfOrdenes() {
        Long clienteId = 1L;
        List<Orden> expectedOrdenes = new ArrayList<>();
        expectedOrdenes.add(new Orden());
        when(ordenRepository.findByClienteId(clienteId)).thenReturn(expectedOrdenes);

        List<Orden> actualOrdenes = ordenService.findOrdenesByClienteId(clienteId);

        assertEquals(expectedOrdenes, actualOrdenes);
    }

    @Test
    void save_ShouldThrowExceptionIfClienteNotFound() {
        OrdenDTO ordenDTO = new OrdenDTO();
        ordenDTO.setCantidad(2);
        ordenDTO.setClienteId(1L);

        when(clienteRepository.findById(ordenDTO.getClienteId())).thenReturn(Optional.empty());

        OrdenNotFoundException exception = assertThrows(OrdenNotFoundException.class, () -> ordenService.save(ordenDTO));
        assertEquals("Producto no encontrado", exception.getMessage());
    }

    @Test
    void update_ShouldThrowExceptionIfOrdenNotFound() {
        Long id = 1L;
        OrdenDTO ordenDTO = new OrdenDTO();
        ordenDTO.setCantidad(2);

        when(ordenRepository.findById(id)).thenReturn(Optional.empty());

        OrdenNotFoundException exception = assertThrows(OrdenNotFoundException.class, () -> ordenService.update(id, ordenDTO));
        assertEquals("Orden no encontrada", exception.getMessage());
    }

    @Test
    void update_ShouldReturnUpdatedOrden() {
        Long id = 1L;
        Long productoId = 1L;
        Long clienteId = 1L;

        OrdenDTO ordenDTO = new OrdenDTO();
        ordenDTO.setCantidad(2);
        ordenDTO.setFechaOrden(new Date());
        ordenDTO.setProductoId(productoId);
        ordenDTO.setClienteId(clienteId);

        Orden existingOrden = new Orden();
        existingOrden.setId(id);
        existingOrden.setCantidad(1);
        existingOrden.setTotal(BigDecimal.valueOf(50.0));

        Producto existingProducto = new Producto();
        existingProducto.setId(productoId);
        existingOrden.setProducto(existingProducto);

        when(ordenRepository.findById(id)).thenReturn(Optional.of(existingOrden));

        Producto producto = new Producto();
        producto.setId(productoId);
        producto.setPrecio(BigDecimal.valueOf(30.0));

        when(productoRepository.findById(productoId)).thenReturn(Optional.of(producto));

        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));

        when(ordenRepository.save(any(Orden.class))).thenReturn(existingOrden);

        Orden actualOrden = ordenService.update(id, ordenDTO);

        assertEquals(ordenDTO.getCantidad(), actualOrden.getCantidad());
        assertEquals(ordenDTO.getFechaOrden(), actualOrden.getFechaOrden());

        BigDecimal expectedTotal = producto.getPrecio().multiply(BigDecimal.valueOf(ordenDTO.getCantidad()));
        assertEquals(expectedTotal, actualOrden.getTotal());
    }



    @Test
    void delete_ShouldInvokeDeleteById() {
        Long id = 1L;

        ordenService.delete(id);

        verify(ordenRepository, times(1)).deleteById(id);
    }
}

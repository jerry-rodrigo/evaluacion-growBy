package com.springboot.microservicio.growby.controller;

import com.springboot.microservicio.growby.dto.ClienteDTO;
import com.springboot.microservicio.growby.model.Cliente;
import com.springboot.microservicio.growby.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteControllerTest {

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllClientes_ShouldReturnListOfClientes() {

        Cliente cliente1 = new Cliente();
        cliente1.setId(1L);
        Cliente cliente2 = new Cliente();
        cliente2.setId(2L);
        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);
        when(clienteService.findAll()).thenReturn(clientes);

        ResponseEntity<List<Cliente>> response = clienteController.getAllClientes();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(clientes, response.getBody());
    }

    @Test
    void createCliente_ShouldReturnCreatedCliente() {

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNombre("John Doe");
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        when(clienteService.save(any(ClienteDTO.class))).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.createCliente(clienteDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(cliente, response.getBody());
    }

    @Test
    void getClienteById_ShouldReturnCliente_WhenExists() {

        Long id = 1L;
        Cliente cliente = new Cliente();
        cliente.setId(id);
        when(clienteService.getClienteById(id)).thenReturn(Optional.of(cliente));

        ResponseEntity<Cliente> response = clienteController.getClienteById(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(cliente, response.getBody());
    }

    @Test
    void getClienteById_ShouldReturnNotFound_WhenDoesNotExist() {

        Long id = 1L;
        when(clienteService.getClienteById(id)).thenReturn(Optional.empty());

        ResponseEntity<Cliente> response = clienteController.getClienteById(id);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void updateCliente_ShouldReturnUpdatedCliente() {

        Long id = 1L;
        ClienteDTO clienteDTO = new ClienteDTO();
        Cliente cliente = new Cliente();
        cliente.setId(id);
        when(clienteService.update(id, clienteDTO)).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.updateCliente(id, clienteDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(cliente, response.getBody());
    }

    @Test
    void deleteCliente_ShouldReturnNoContent() {
        Long id = 1L;
        doNothing().when(clienteService).delete(id);

        ResponseEntity<String> response = clienteController.deleteCliente(id);

        assertEquals(200, response.getStatusCodeValue()); // Cambiar a 200
        assertEquals("Cliente eliminado con éxito.", response.getBody()); // Verificar el mensaje
        verify(clienteService, times(1)).delete(id);
    }

    @Test
    void deleteCliente_ShouldReturnBadRequest_WhenDataIntegrityViolation() {
        Long id = 1L;

        doThrow(new DataIntegrityViolationException("")).when(clienteService).delete(id);

        ResponseEntity<String> response = clienteController.deleteCliente(id);

        assertEquals(400, response.getStatusCodeValue()); // Verificar el estado 400
        assertEquals("No se puede eliminar el cliente porque tiene órdenes asociadas.", response.getBody()); // Verificar el mensaje
        verify(clienteService, times(1)).delete(id);
    }

}

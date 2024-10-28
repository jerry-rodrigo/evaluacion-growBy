package com.springboot.microservicio.growby.service.impl;

import com.springboot.microservicio.growby.dto.ClienteDTO;
import com.springboot.microservicio.growby.exception.ClienteNotFoundException;
import com.springboot.microservicio.growby.model.Cliente;
import com.springboot.microservicio.growby.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteServiceImplTest {

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_ShouldReturnListOfClientes() {
        Cliente cliente1 = new Cliente();
        cliente1.setId(1L);
        Cliente cliente2 = new Cliente();
        cliente2.setId(2L);
        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);
        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> result = clienteService.findAll();

        assertEquals(2, result.size());
        assertEquals(clientes, result);
    }

    @Test
    void getClienteById_ShouldReturnClienteIfExists() {
        Long id = 1L;
        Cliente cliente = new Cliente();
        cliente.setId(id);
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        Optional<Cliente> result = clienteService.getClienteById(id);

        assertTrue(result.isPresent());
        assertEquals(cliente, result.get());
    }

    @Test
    void getClienteById_ShouldReturnEmptyIfNotExists() {
        Long id = 1L;
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Cliente> result = clienteService.getClienteById(id);

        assertFalse(result.isPresent());
    }

    @Test
    void save_ShouldReturnSavedCliente() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNombre("Juan");
        clienteDTO.setEmail("juan@example.com");
        clienteDTO.setTelefono("123456789");

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        when(clienteRepository.findByEmail(clienteDTO.getEmail())).thenReturn(Optional.empty());
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente result = clienteService.save(clienteDTO);

        assertEquals(cliente, result);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void save_ShouldThrowExceptionIfEmailExists() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setEmail("juan@example.com");

        Cliente existingCliente = new Cliente();
        existingCliente.setId(1L);
        when(clienteRepository.findByEmail(clienteDTO.getEmail())).thenReturn(Optional.of(existingCliente));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> clienteService.save(clienteDTO));
        assertEquals("El email ya está en uso", exception.getMessage());
    }

    @Test
    void update_ShouldReturnUpdatedCliente() {
        Long id = 1L;
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNombre("Juan Actualizado");
        clienteDTO.setEmail("juan_actualizado@example.com");
        Cliente existingCliente = new Cliente();
        existingCliente.setId(id);
        when(clienteRepository.findById(id)).thenReturn(Optional.of(existingCliente));
        when(clienteRepository.findByEmail(clienteDTO.getEmail())).thenReturn(Optional.empty());
        when(clienteRepository.save(any(Cliente.class))).thenReturn(existingCliente);

        Cliente result = clienteService.update(id, clienteDTO);

        assertEquals(existingCliente, result);
        assertEquals(clienteDTO.getNombre(), result.getNombre());
    }

    @Test
    void update_ShouldThrowClienteNotFoundExceptionIfNotExists() {
        Long id = 1L;
        ClienteDTO clienteDTO = new ClienteDTO();
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        ClienteNotFoundException exception = assertThrows(ClienteNotFoundException.class, () -> clienteService.update(id, clienteDTO));
        assertEquals("Cliente no encontrado", exception.getMessage());
    }


    @Test
    void delete_ShouldCallRepositoryDeleteById() {
        Long id = 1L;

        clienteService.delete(id);

        verify(clienteRepository, times(1)).deleteById(id);
    }
}

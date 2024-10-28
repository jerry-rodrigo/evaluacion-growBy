package com.springboot.microservicio.growby.service.impl;

import com.springboot.microservicio.growby.dto.ClienteDTO;
import com.springboot.microservicio.growby.exception.ClienteNotFoundException;
import com.springboot.microservicio.growby.model.Cliente;
import com.springboot.microservicio.growby.repository.ClienteRepository;
import com.springboot.microservicio.growby.service.ClienteService;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio {@link ClienteService} que maneja la lógica de negocio
 * relacionada con la entidad {@link Cliente}.
 */
@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /**
     * Recupera todos los clientes.
     *
     * @return Una lista de clientes.
     */
    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    /**
     * Recupera un cliente por su identificador.
     *
     * @param id El identificador del cliente.
     * @return Un {@link Optional} que contiene el cliente encontrado, o vacío si no se encuentra.
     */
    @Override
    public Optional<Cliente> getClienteById(Long id) {
        return clienteRepository.findById(id);
    }

    /**
     * Guarda un nuevo cliente.
     *
     * @param clienteDTO Los datos del cliente a guardar.
     * @return El cliente guardado.
     * @throws RuntimeException si el email ya está en uso.
     */
    @Override
    public Cliente save(@Valid ClienteDTO clienteDTO) {
        if (emailExists(clienteDTO.getEmail())) {
            throw new RuntimeException("El email ya está en uso");
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefono(clienteDTO.getTelefono());
        return clienteRepository.save(cliente);
    }

    /**
     * Actualiza un cliente existente.
     *
     * @param id         El identificador del cliente a actualizar.
     * @param clienteDTO Los nuevos datos del cliente.
     * @return El cliente actualizado.
     * @throws ClienteNotFoundException si el cliente no se encuentra.
     * @throws RuntimeException         si el email ya está en uso.
     */
    @Override
    public Cliente update(Long id, @Valid ClienteDTO clienteDTO) {
        Optional<Cliente> optionalCliente = clienteRepository.findById(id);
        if (optionalCliente.isPresent()) {
            if (emailExists(clienteDTO.getEmail(), id)) {
                throw new RuntimeException("El email ya está en uso");
            }

            Cliente cliente = optionalCliente.get();
            cliente.setNombre(clienteDTO.getNombre());
            cliente.setEmail(clienteDTO.getEmail());
            cliente.setTelefono(clienteDTO.getTelefono());
            return clienteRepository.save(cliente);
        } else {
            throw new ClienteNotFoundException("Cliente no encontrado");
        }
    }

    /**
     * Elimina un cliente por su identificador.
     *
     * @param id El identificador del cliente a eliminar.
     */
    @Override
    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }

    /**
     * Contador de todos los clientes.
     *
     */
    @Override
    public long countAllClientes() {
        return clienteRepository.count();
    }

    private boolean emailExists(String email) {
        return clienteRepository.findByEmail(email).isPresent();
    }

    private boolean emailExists(String email, Long id) {
        Optional<Cliente> cliente = clienteRepository.findByEmail(email);
        return cliente.isPresent() && !cliente.get().getId().equals(id);
    }
}

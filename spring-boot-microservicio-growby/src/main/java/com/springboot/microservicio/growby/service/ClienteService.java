package com.springboot.microservicio.growby.service;

import com.springboot.microservicio.growby.dto.ClienteDTO;
import com.springboot.microservicio.growby.model.Cliente;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define los métodos para la gestión de clientes en el sistema.
 * Proporciona operaciones CRUD para la entidad {@link Cliente}.
 */
public interface ClienteService {

    /**
     * Recupera todos los clientes.
     *
     * @return Una lista de objetos {@link Cliente}.
     */
    List<Cliente> findAll();

    /**
     * Recupera un cliente por su identificador.
     *
     * @param id El identificador del cliente.
     * @return Un {@link Optional} que contiene el cliente encontrado, o vacío si no se encuentra.
     */
    Optional<Cliente> getClienteById(Long id);

    /**
     * Guarda un nuevo cliente.
     *
     * @param clienteDTO Los datos del cliente a guardar.
     * @return El cliente guardado.
     */
    Cliente save(ClienteDTO clienteDTO);

    /**
     * Actualiza un cliente existente.
     *
     * @param id El identificador del cliente a actualizar.
     * @param clienteDTO Los nuevos datos del cliente.
     * @return El cliente actualizado.
     */
    Cliente update(Long id, ClienteDTO clienteDTO);

    /**
     * Elimina un cliente por su identificador.
     *
     * @param id El identificador del cliente a eliminar.
     */
    void delete(Long id);

    /**
     * Contador de todos los clientes.
     *
     */
    long countAllClientes();
}

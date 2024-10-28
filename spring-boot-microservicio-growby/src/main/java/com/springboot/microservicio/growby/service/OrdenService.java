package com.springboot.microservicio.growby.service;

import com.springboot.microservicio.growby.dto.OrdenDTO;
import com.springboot.microservicio.growby.model.Orden;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define los métodos para la gestión de órdenes en el sistema.
 * Proporciona operaciones CRUD para la entidad {@link Orden}.
 */
public interface OrdenService {

    /**
     * Recupera todas las órdenes.
     *
     * @return Una lista de objetos {@link Orden}.
     */
    List<Orden> findAll();

    /**
     * Recupera las órdenes asociadas a un cliente específico.
     *
     * @param clienteId El identificador del cliente cuyas órdenes se desean recuperar.
     * @return Una lista de órdenes asociadas al cliente.
     */
    List<Orden> findOrdenesByClienteId(Long clienteId);

    /**
     * Guarda una nueva orden.
     *
     * @param ordenDTO Los datos de la orden a guardar.
     * @return La orden guardada.
     */
    Orden save(OrdenDTO ordenDTO);

    /**
     * Actualiza una orden existente.
     *
     * @param id El identificador de la orden a actualizar.
     * @param ordenDTO Los nuevos datos de la orden.
     * @return La orden actualizada.
     */
    Orden update(Long id, OrdenDTO ordenDTO);

    /**
     * Elimina una orden por su identificador.
     *
     * @param id El identificador de la orden a eliminar.
     */
    void delete(Long id);

    /**
     * Recupera una orden por su identificador.
     *
     * @param id El identificador de la orden a buscar.
     */
    Optional<Orden> findById(Long id);

    /**
     * Contador de todos las ordenes.
     *
     */
    long countAllOrdenes();
}

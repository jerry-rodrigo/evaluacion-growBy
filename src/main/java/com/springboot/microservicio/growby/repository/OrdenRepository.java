package com.springboot.microservicio.growby.repository;

import com.springboot.microservicio.growby.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio para manejar operaciones de acceso a datos relacionadas con la entidad {@link Orden}.
 * Extiende la interfaz {@link JpaRepository}, proporcionando métodos CRUD y operaciones de consulta
 * para la entidad Orden.
 */
public interface OrdenRepository extends JpaRepository<Orden, Long> {
    /**
     * Busca todas las órdenes asociadas a un cliente específico.
     *
     * @param clienteId El identificador del cliente cuyas órdenes se desean buscar.
     * @return Una lista de órdenes asociadas al cliente.
     */
    List<Orden> findByClienteId(Long clienteId);
}

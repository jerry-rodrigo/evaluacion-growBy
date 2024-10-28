package com.springboot.microservicio.growby.repository;

import com.springboot.microservicio.growby.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para manejar operaciones de acceso a datos relacionadas con la entidad {@link Producto}.
 * Extiende la interfaz {@link JpaRepository}, proporcionando métodos CRUD y operaciones de consulta
 * para la entidad Producto.
 */
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Este repositorio hereda todos los métodos CRUD de JpaRepository.
}

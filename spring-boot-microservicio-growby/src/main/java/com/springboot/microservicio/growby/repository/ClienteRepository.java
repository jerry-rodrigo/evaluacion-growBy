package com.springboot.microservicio.growby.repository;

import com.springboot.microservicio.growby.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio para manejar operaciones de acceso a datos relacionadas con la entidad {@link Cliente}.
 * Extiende la interfaz {@link JpaRepository}, proporcionando métodos CRUD y operaciones de consulta
 * para la entidad Cliente.
 */
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    /**
     * Busca un cliente por su dirección de correo electrónico.
     *
     * @param email La dirección de correo electrónico del cliente.
     * @return Un {@link Optional} que contiene el cliente encontrado, o vacío si no se encuentra.
     */
    Optional<Cliente> findByEmail(String email);
}

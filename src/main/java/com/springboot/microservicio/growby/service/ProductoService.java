package com.springboot.microservicio.growby.service;

import com.springboot.microservicio.growby.dto.ProductoDTO;
import com.springboot.microservicio.growby.model.Producto;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define los métodos para la gestión de productos en el sistema.
 * Proporciona operaciones CRUD para la entidad {@link Producto}.
 */
public interface ProductoService {

    /**
     * Recupera todos los productos.
     *
     * @return Una lista de objetos {@link Producto}.
     */
    List<Producto> findAll();

    /**
     * Recupera un producto por su identificador.
     *
     * @param id El identificador del producto.
     * @return Un {@link Optional} que contiene el producto encontrado, o vacío si no se encuentra.
     */
    Optional<Producto> getProductoById(Long id);

    /**
     * Guarda un nuevo producto.
     *
     * @param productoDTO Los datos del producto a guardar.
     * @return El producto guardado.
     */
    Producto save(ProductoDTO productoDTO);

    /**
     * Actualiza un producto existente.
     *
     * @param id El identificador del producto a actualizar.
     * @param productoDTO Los nuevos datos del producto.
     * @return El producto actualizado.
     */
    Producto update(Long id, ProductoDTO productoDTO);

    /**
     * Elimina un producto por su identificador.
     *
     * @param id El identificador del producto a eliminar.
     */
    void delete(Long id);

    /**
     * Contador de todos los productos.
     *
     */
    long countAllProducts();
}

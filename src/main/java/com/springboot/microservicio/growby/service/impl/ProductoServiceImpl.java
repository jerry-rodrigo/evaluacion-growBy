package com.springboot.microservicio.growby.service.impl;

import com.springboot.microservicio.growby.dto.ProductoDTO;
import com.springboot.microservicio.growby.exception.ProductoNotFoundException;
import com.springboot.microservicio.growby.model.Producto;
import com.springboot.microservicio.growby.repository.ProductoRepository;
import com.springboot.microservicio.growby.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio {@link ProductoService} que maneja la lógica de negocio
 * relacionada con la entidad {@link Producto}.
 */
@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    /**
     * Recupera todos los productos.
     *
     * @return Una lista de productos.
     */
    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    /**
     * Recupera un producto por su identificador.
     *
     * @param id El identificador del producto.
     * @return Un {@link Optional} que contiene el producto encontrado, o vacío si no se encuentra.
     */
    @Override
    public Optional<Producto> getProductoById(Long id) {
        return productoRepository.findById(id);
    }

    /**
     * Guarda un nuevo producto.
     *
     * @param productoDTO Los datos del producto a guardar.
     * @return El producto guardado.
     */
    @Override
    public Producto save(ProductoDTO productoDTO) {
        Producto producto = new Producto();
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setFechaCreacion(new Date());
        producto.setEstado(Producto.Estado.fromString(productoDTO.getEstado()));
        return productoRepository.save(producto);
    }

    /**
     * Actualiza un producto existente.
     *
     * @param id           El identificador del producto a actualizar.
     * @param productoDTO  Los nuevos datos del producto.
     * @return El producto actualizado.
     * @throws ProductoNotFoundException si el producto no se encuentra.
     */
    @Override
    public Producto update(Long id, ProductoDTO productoDTO) {
        Optional<Producto> optionalProducto = productoRepository.findById(id);
        if (optionalProducto.isPresent()) {
            Producto producto = optionalProducto.get();
            producto.setNombre(productoDTO.getNombre());
            producto.setDescripcion(productoDTO.getDescripcion());
            producto.setPrecio(productoDTO.getPrecio());
            producto.setEstado(Producto.Estado.valueOf(productoDTO.getEstado()));
            return productoRepository.save(producto);
        } else {
            throw new ProductoNotFoundException("Producto no encontrado");
        }
    }

    /**
     * Elimina un producto por su identificador.
     *
     * @param id El identificador del producto a eliminar.
     */
    @Override
    public void delete(Long id) {
        productoRepository.deleteById(id);
    }

    /**
     * Contador de todos los productos.
     *
     */
    @Override
    public long countAllProducts() {
        return productoRepository.count();
    }
}

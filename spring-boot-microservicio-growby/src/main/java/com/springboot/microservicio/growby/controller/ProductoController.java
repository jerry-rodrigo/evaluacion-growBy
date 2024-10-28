package com.springboot.microservicio.growby.controller;

import com.springboot.microservicio.growby.dto.ProductoDTO;
import com.springboot.microservicio.growby.model.Producto;
import com.springboot.microservicio.growby.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controlador que gestiona las operaciones relacionadas con los productos.
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    /**
     * Obtiene la lista de todos los productos.
     *
     * @return Lista de productos.
     */
    @GetMapping
    public ResponseEntity<List<Producto>> getAllProductos() {
        List<Producto> productos = productoService.findAll();
        return ResponseEntity.ok(productos);
    }

    /**
     * Obtiene un producto por su identificador.
     *
     * @param id Identificador del producto.
     * @return El producto correspondiente, si existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        return productoService.getProductoById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo producto.
     *
     * @param productoDTO Objeto que contiene los datos del nuevo producto.
     * @return El producto creado.
     */
    @PostMapping
    public ResponseEntity<Producto> createProducto(@Valid @RequestBody ProductoDTO productoDTO) {
        Producto producto = productoService.save(productoDTO);
        return ResponseEntity.status(201).body(producto);
    }

    /**
     * Actualiza un producto existente.
     *
     * @param id Identificador del producto a actualizar.
     * @param productoDTO Objeto que contiene los nuevos datos del producto.
     * @return El producto actualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable Long id, @Valid @RequestBody ProductoDTO productoDTO) {
        Producto producto = productoService.update(id, productoDTO);
        return ResponseEntity.ok(producto);
    }

    /**
     * Elimina un producto por su identificador.
     *
     * @param id Identificador del producto a eliminar.
     * @return Código de estado 204 No Content si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Contador de todos las Prodcutos.
     *
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countProducts() {
        long totalProductos = productoService.countAllProducts();
        return ResponseEntity.ok(totalProductos);
    }

}

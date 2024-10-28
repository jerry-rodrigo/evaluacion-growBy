package com.springboot.microservicio.growby.controller;

import com.springboot.microservicio.growby.dto.OrdenDTO;
import com.springboot.microservicio.growby.model.Orden;
import com.springboot.microservicio.growby.service.OrdenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Controlador que gestiona las operaciones relacionadas con las órdenes.
 */
@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {

    private final OrdenService ordenService;

    public OrdenController(OrdenService ordenService) {
        this.ordenService = ordenService;
    }

    /**
     * Obtiene la lista de todas las órdenes.
     *
     * @return Lista de órdenes.
     */
    @GetMapping
    public ResponseEntity<List<Orden>> getAll() {
        List<Orden> ordenes = ordenService.findAll();
        return ResponseEntity.ok(ordenes);
    }

    /**
     * Obtiene la lista de órdenes asociadas a un cliente por su identificador.
     *
     * @param clienteId Identificador del cliente.
     * @return Lista de órdenes del cliente.
     */
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Orden>> getOrdenesByClienteId(@PathVariable Long clienteId) {
        List<Orden> ordenes = ordenService.findOrdenesByClienteId(clienteId);
        return ResponseEntity.ok(ordenes);
    }

    /**
     * Obtiene la lista de órdenes.
     *
     * @param id Objeto que contiene los datos de la nueva orden.
     * @return La orden creada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Orden>> getOrdenById(@PathVariable Long id) {
        Optional<Orden> orden = ordenService.findById(id);
        if (orden == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orden);
    }

    /**
     * Crea una nueva orden.
     *
     * @param ordenDTO Objeto que contiene los datos de la nueva orden.
     * @return La orden creada.
     */
    @PostMapping
    public ResponseEntity<Orden> createOrder(@Valid @RequestBody OrdenDTO ordenDTO) {
        Orden orden = ordenService.save(ordenDTO);
        return ResponseEntity.status(201).body(orden);
    }

    /**
     * Actualiza una orden existente.
     *
     * @param id Identificador de la orden a actualizar.
     * @param ordenDTO Objeto que contiene los nuevos datos de la orden.
     * @return La orden actualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Orden> updateOrder(@PathVariable Long id, @Valid @RequestBody OrdenDTO ordenDTO) {
        Orden orden = ordenService.update(id, ordenDTO);
        return ResponseEntity.ok(orden);
    }

    /**
     * Elimina una orden por su identificador.
     *
     * @param id Identificador de la orden a eliminar.
     * @return Código de estado 204 No Content si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        ordenService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Contador de todos las Ordenes.
     *
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countOrdenes() {
        long totalOrdenes = ordenService.countAllOrdenes();
        return ResponseEntity.ok(totalOrdenes);
    }

}

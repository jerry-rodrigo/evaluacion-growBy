package com.springboot.microservicio.growby.controller;

import com.springboot.microservicio.growby.dto.ClienteDTO;
import com.springboot.microservicio.growby.model.Cliente;
import com.springboot.microservicio.growby.service.ClienteService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controlador que gestiona las operaciones relacionadas con los clientes.
 */
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /**
     * Obtiene la lista de todos los clientes.
     *
     * @return Lista de clientes.
     */
    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> clientes = clienteService.findAll();
        return ResponseEntity.ok(clientes);
    }

    /**
     * Crea un nuevo cliente.
     *
     * @param clienteDTO Objeto que contiene los datos del nuevo cliente.
     * @return El cliente creado.
     */
    @PostMapping
    public ResponseEntity<Cliente> createCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = clienteService.save(clienteDTO);
        return ResponseEntity.status(201).body(cliente);
    }

    /**
     * Obtiene un cliente por su identificador.
     *
     * @param id Identificador del cliente.
     * @return El cliente correspondiente, si existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        return clienteService.getClienteById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Actualiza un cliente existente.
     *
     * @param id Identificador del cliente a actualizar.
     * @param clienteDTO Objeto que contiene los nuevos datos del cliente.
     * @return El cliente actualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = clienteService.update(id, clienteDTO);
        return ResponseEntity.ok(cliente);
    }

    /**
     * Elimina un cliente por su identificador.
     *
     * @param id Identificador del cliente a eliminar.
     * @return Código de estado 204 No Content si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable Long id) {
        try {
            clienteService.delete(id);
            return ResponseEntity.ok("Cliente eliminado con éxito.");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se puede eliminar el cliente porque tiene órdenes asociadas.");
        }
    }

    /**
     * Contador de todos los clientes.
     *
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countClientes() {
        long totalClientes = clienteService.countAllClientes();
        return ResponseEntity.ok(totalClientes);
    }

}

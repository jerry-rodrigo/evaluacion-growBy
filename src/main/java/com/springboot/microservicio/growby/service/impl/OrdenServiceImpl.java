package com.springboot.microservicio.growby.service.impl;

import com.springboot.microservicio.growby.dto.OrdenDTO;
import com.springboot.microservicio.growby.exception.OrdenNotFoundException;
import com.springboot.microservicio.growby.model.Cliente;
import com.springboot.microservicio.growby.model.Orden;
import com.springboot.microservicio.growby.model.Producto;
import com.springboot.microservicio.growby.repository.ClienteRepository;
import com.springboot.microservicio.growby.repository.OrdenRepository;
import com.springboot.microservicio.growby.repository.ProductoRepository;
import com.springboot.microservicio.growby.service.OrdenService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio {@link OrdenService} que maneja la lógica de negocio
 * relacionada con la entidad {@link Orden}.
 */
@Service
public class OrdenServiceImpl implements OrdenService {

    private final OrdenRepository ordenRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;

    public OrdenServiceImpl(OrdenRepository ordenRepository, ClienteRepository clienteRepository, ProductoRepository productoRepository) {
        this.ordenRepository = ordenRepository;
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
    }

    /**
     * Recupera todas las órdenes.
     *
     * @return Una lista de órdenes.
     */
    @Override
    public List<Orden> findAll() {
        return ordenRepository.findAll();
    }

    /**
     * Recupera las órdenes asociadas a un cliente específico.
     *
     * @param clienteId El identificador del cliente cuyas órdenes se desean recuperar.
     * @return Una lista de órdenes asociadas al cliente.
     */
    @Override
    public List<Orden> findOrdenesByClienteId(Long clienteId) {
        return ordenRepository.findByClienteId(clienteId);
    }

    /**
     * Guarda una nueva orden.
     *
     * @param ordenDTO Los datos de la orden a guardar.
     * @return La orden guardada.
     * @throws OrdenNotFoundException si el cliente asociado no se encuentra.
     */
    @Override
    public Orden save(OrdenDTO ordenDTO) {

        Optional<Producto> productoOpt = productoRepository.findById(ordenDTO.getProductoId());
        if (!productoOpt.isPresent()) {
            throw new OrdenNotFoundException("Producto no encontrado");
        }

        Producto producto = productoOpt.get();
        BigDecimal precioUnitario = producto.getPrecio();
        int cantidad = ordenDTO.getCantidad();

        BigDecimal total = precioUnitario.multiply(BigDecimal.valueOf(cantidad));

        Orden orden = new Orden();
        orden.setCantidad(cantidad);
        orden.setTotal(total);
        orden.setFechaOrden(new Date());

        Optional<Cliente> clienteOpt = clienteRepository.findById(ordenDTO.getClienteId());
        if (clienteOpt.isPresent()) {
            orden.setCliente(clienteOpt.get());
        } else {
            throw new OrdenNotFoundException("Cliente no encontrado");
        }

        orden.setProducto(producto);

        return ordenRepository.save(orden);
    }

    /**
     * Actualiza una orden existente.
     *
     * @param id       El identificador de la orden a actualizar.
     * @param ordenDTO Los nuevos datos de la orden.
     * @return La orden actualizada.
     * @throws OrdenNotFoundException si la orden no se encuentra.
     */
    @Override
    public Orden update(Long id, OrdenDTO ordenDTO) {
        Optional<Orden> optionalOrden = ordenRepository.findById(id);
        if (optionalOrden.isPresent()) {
            Orden orden = optionalOrden.get();

            orden.setCantidad(ordenDTO.getCantidad());

            Optional<Producto> productoOpt = productoRepository.findById(ordenDTO.getProductoId());
            if (productoOpt.isEmpty()) {
                throw new OrdenNotFoundException("Producto no encontrado");
            }
            Producto producto = productoOpt.get();
            orden.setProducto(producto);

            BigDecimal precioUnitario = producto.getPrecio();
            BigDecimal nuevoTotal = precioUnitario.multiply(BigDecimal.valueOf(ordenDTO.getCantidad()));
            orden.setTotal(nuevoTotal);

            orden.setFechaOrden(ordenDTO.getFechaOrden() != null ? ordenDTO.getFechaOrden() : new Date());

            Optional<Cliente> clienteOpt = clienteRepository.findById(ordenDTO.getClienteId());
            if (clienteOpt.isPresent()) {
                orden.setCliente(clienteOpt.get());
            } else {
                throw new OrdenNotFoundException("Cliente no encontrado");
            }

            return ordenRepository.save(orden);
        } else {
            throw new OrdenNotFoundException("Orden no encontrada");
        }
    }


    /**
     * Elimina una orden por su identificador.
     *
     * @param id El identificador de la orden a eliminar.
     */
    @Override
    public void delete(Long id) {
        ordenRepository.deleteById(id);
    }

    /**
     * Recupera una orden por su identificador.
     *
     * @param id El identificador de la orden a buscar.
     */
    @Override
    public Optional<Orden> findById(Long id) {
        return ordenRepository.findById(id);
    }

    /**
     * Contador de todos las Ordenes.
     *
     */
    @Override
    public long countAllOrdenes() {
        return ordenRepository.count();
    }
}

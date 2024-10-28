package com.springboot.microservicio.growby.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.util.Date;

/**
 * Data Transfer Object (DTO) que representa una orden.
 * Este objeto es utilizado para la transferencia de datos entre
 * el cliente y el servidor en operaciones de creación y actualización de órdenes.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdenDTO {

    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private int cantidad;
    private Date fechaOrden;
    private Long clienteId;
    private Long productoId;
}
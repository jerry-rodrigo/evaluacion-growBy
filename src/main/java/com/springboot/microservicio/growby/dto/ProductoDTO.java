package com.springboot.microservicio.growby.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) que representa un producto.
 * Este objeto es utilizado para la transferencia de datos entre
 * el cliente y el servidor en operaciones de creación y actualización de productos.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100)
    private String nombre;

    @Size(max = 500)
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin("0.0")
    @Positive(message = "El precio debe ser positivo")
    private BigDecimal precio;

    @NotEmpty
    private String estado;
}

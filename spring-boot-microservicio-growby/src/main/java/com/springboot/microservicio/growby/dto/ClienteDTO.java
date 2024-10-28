package com.springboot.microservicio.growby.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) que representa a un cliente.
 * Este objeto es utilizado para la transferencia de datos entre
 * el cliente y el servidor en operaciones de creación y actualización.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Email no válido")
    private String email;

    private String telefono;
}

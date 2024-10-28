package com.springboot.microservicio.growby.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Clase que representa un Producto en el sistema.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private BigDecimal precio;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    public enum Estado {
        ACTIVO,
        INACTIVO;

        public static Estado fromString(String estado) {
            if (estado == null) {
                throw new IllegalArgumentException("El estado no puede ser nulo");
            }

            switch (estado.toUpperCase()) {
                case "ACTIVO":
                case "activo":
                case "Activo":
                    return ACTIVO;
                case "INACTIVO":
                case "inactivo":
                case "Inactivo":
                    return INACTIVO;
                default:
                    throw new IllegalArgumentException("Estado no válido: " + estado);
            }
        }
    }

}

package com.springboot.microservicio.growby.exception;

/**
 * Excepción lanzada cuando una orden no se encuentra en el sistema.
 * Esta excepción se extiende de {@link RuntimeException} y se utiliza
 * para indicar que una orden solicitada no está disponible.
 */
public class OrdenNotFoundException extends RuntimeException {
    /**
     * Constructor que recibe un mensaje de error.
     *
     * @param message Mensaje que describe la excepción.
     */
    public OrdenNotFoundException(String message) {
        super(message);
    }
}

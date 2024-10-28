package com.springboot.microservicio.growby.exception;

/**
 * Excepción lanzada cuando un producto no se encuentra en el sistema.
 * Esta excepción se extiende de {@link RuntimeException} y se utiliza
 * para indicar que un producto solicitado no está disponible.
 */
public class ProductoNotFoundException extends RuntimeException {
    /**
     * Constructor que recibe un mensaje de error.
     *
     * @param message Mensaje que describe la excepción.
     */
    public ProductoNotFoundException(String message) {
        super(message);
    }
}

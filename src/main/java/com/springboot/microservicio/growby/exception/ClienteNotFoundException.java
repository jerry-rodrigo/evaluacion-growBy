package com.springboot.microservicio.growby.exception;

/**
 * Excepción lanzada cuando un cliente no se encuentra en el sistema.
 * Esta excepción se extiende de {@link RuntimeException} y se utiliza
 * para indicar que un cliente solicitado no está disponible.
 */
public class ClienteNotFoundException extends RuntimeException {
    /**
     * Constructor que recibe un mensaje de error.
     *
     * @param message Mensaje que describe la excepción.
     */
    public ClienteNotFoundException(String message) {
        super(message);
    }
}

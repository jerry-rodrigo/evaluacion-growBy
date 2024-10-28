package com.springboot.microservicio.growby.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase para manejar excepciones de forma global en la aplicación.
 * Utiliza la anotación {@link ControllerAdvice} para capturar
 * excepciones lanzadas por los controladores y proporcionar
 * respuestas adecuadas a los clientes.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja las excepciones de tipo {@link RuntimeException}.
     *
     * @param e La excepción lanzada.
     * @return Una respuesta HTTP con estado 404 Not Found y el mensaje de error.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    /**
     * Maneja excepciones de validación cuando se producen errores en los
     * argumentos de los métodos.
     *
     * @param ex La excepción lanzada al validar argumentos.
     * @return Un mapa de errores con los nombres de los campos y sus mensajes de error,
     *         junto con una respuesta HTTP 400 Bad Request.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}

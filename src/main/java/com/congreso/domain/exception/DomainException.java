package com.congreso.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Excepción genérica de dominio. Sustituye a las excepciones específicas del dominio.
 * Extiende ResponseStatusException para integrarse con el manejo HTTP de Spring.
 */
public class DomainException extends ResponseStatusException {
    private final String type;

    public DomainException(HttpStatus status, String message) {
        super(status, message);
        this.type = null;
    }

    public DomainException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
        this.type = null;
    }

    public DomainException(String type, HttpStatus status, String message) {
        super(status, message);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    /**
     * Conveniencia que devuelve el código numérico HTTP asociado.
     */
    public int getStatus() {
        return getStatusCode().value();
    }
}


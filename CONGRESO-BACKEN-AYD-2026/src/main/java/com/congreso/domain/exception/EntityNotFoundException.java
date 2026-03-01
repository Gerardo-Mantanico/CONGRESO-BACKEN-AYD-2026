package com.congreso.domain.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends DomainException {
    public EntityNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public EntityNotFoundException(String entityName, Long id) {
        super(HttpStatus.NOT_FOUND, String.format("%s con id %d no encontrado", entityName, id));
    }
}

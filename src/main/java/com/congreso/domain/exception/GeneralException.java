package com.congreso.domain.exception;

import org.springframework.http.HttpStatus;

public class GeneralException extends DomainException{
    private final String type;

    public GeneralException(String type, String message) {
        super(type, HttpStatus.BAD_REQUEST, message);
        this.type = type;
    }

    // Nuevo constructor que permite especificar el HttpStatus en la construcción (inmutable)
    public GeneralException(String type, String message, HttpStatus status) {
        super(type, status == null ? HttpStatus.BAD_REQUEST : status, message);
        this.type = type;
    }

    public int getStatus() {
        return super.getStatus();
    }

    public String getType() {
        return type;
    }
}

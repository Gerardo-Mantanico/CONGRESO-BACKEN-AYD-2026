package com.congreso.domain.utils;

import com.congreso.domain.exception.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class DateRangeValidator {

    public DateRangeValidator() {}

    /**
     * Valida que start < end. Lanza DomainException (BAD_REQUEST) si no.
     * Si alguno es null lanza DomainException con el mensaje proporcionado.
     */
    public void validateStartBeforeEnd(OffsetDateTime start, OffsetDateTime end, String message) {
        if (start == null || end == null) {
            throw new DomainException(HttpStatus.BAD_REQUEST, message);
        }
        if (!start.isBefore(end)) {
            throw new DomainException(HttpStatus.BAD_REQUEST, message);
        }
    }
}

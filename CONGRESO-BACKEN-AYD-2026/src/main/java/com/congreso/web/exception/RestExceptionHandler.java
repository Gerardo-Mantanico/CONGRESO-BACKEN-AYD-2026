package com.congreso.web.exception;

import com.congreso.domain.exception.EntityNotFoundException;
import com.congreso.domain.exception.GeneralException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.congreso.web.response.ApiError;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    private static final Logger log = LogManager.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ApiError>> handleException(MethodArgumentNotValidException exception) {
        List<ApiError> errors = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.add(new ApiError(fieldError.getField(), fieldError.getDefaultMessage()));
        });

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiError> handleException(GeneralException ex) {
        ApiError error = new ApiError(ex.getType(), ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(error);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(EntityNotFoundException ex) {
        ApiError error = new ApiError("not-found", ex.getReason());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> handleResponseStatus(ResponseStatusException ex) {
        ApiError error = new ApiError("error", ex.getReason());
        return ResponseEntity.status(ex.getStatusCode().value()).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex) {
        log.error("Unknown error", ex);

        ApiError error = new ApiError("unknown-error", ex.getMessage());
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (ex instanceof BadCredentialsException) {
            error = new ApiError("wrong-credentials", "Usuario o contraseña incorrectos");
            status = HttpStatus.UNAUTHORIZED;
        }

        if (ex instanceof AccountStatusException) {
            error = new ApiError("locked-account", "La cuenta está bloqueada");
            status = HttpStatus.FORBIDDEN;
        }

        if (ex instanceof AccessDeniedException) {
            error = new ApiError("access-denied", "No tienes permisos para acceder a este recurso");
            status = HttpStatus.FORBIDDEN;
        }

        if (ex instanceof SignatureException) {
            error = new ApiError("bad-signature", "The JWT signature is invalid");
            status = HttpStatus.FORBIDDEN;
        }

        if (ex instanceof ExpiredJwtException) {
            error = new ApiError("token-expired", "Tu sesión ha caducado");
            status = HttpStatus.FORBIDDEN;
        }


        return ResponseEntity.status(status).body(error);
    }
}

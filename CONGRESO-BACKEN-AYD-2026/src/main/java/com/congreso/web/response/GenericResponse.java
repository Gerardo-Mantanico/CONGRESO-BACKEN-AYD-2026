package com.congreso.web.response;

public record GenericResponse(
        int status,
        String message
) {
}

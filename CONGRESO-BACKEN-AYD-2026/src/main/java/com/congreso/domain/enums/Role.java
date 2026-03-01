package com.congreso.domain.enums;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN(1L),
    ADMIN_CONGRESO(2L),
    COMITE_CIENTIFICO(3L),
    PONENTE_INVITADO(4L),
    PARTICIPANTE(5L),
    PONENTE(6L),
    TALLERISTA(7L),
    ASISTENTE(8L);
    private final Long id;

    private Role(Long id) {
        this.id = id;
    }
}


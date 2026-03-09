package com.congreso.domain.enums;

public enum Estados {
    PENDIENTE(1L, "Pendiente"),
    EN_ESPERA(2L, "En espera"),
    ACTIVO(3L, "Activo"),
    EN_PROCESO(4L, "En proceso"),
    ACEPTADO(5L, "Aceptado"),
    RECHAZADO(6L, "Rechazado"),
    CANCELADO(7L, "Cancelado"),
    COMPLETADO(8L, "Completado"),
    OCUPADO(9L, "Ocupado"),
    INACTIVO(10L, "Inactivo"),
    SUSPENDIDO(11L, "Suspendido"),
    FINALIZADO(12L, "Finalizado");

    private final Long id;
    private final String label;

    Estados(Long id, String label) {
        this.id = id;
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static Estados fromLabel(String label) {
        if (label == null) return null;
        for (Estados e : values()) {
            if (e.label.equalsIgnoreCase(label.trim())) {
                return e;
            }
        }
        return null;
    }

    public static Estados fromId(Long id) {
        if (id == null) return null;
        for (Estados e : values()) {
            if (e.id.equals(id)) return e;
        }
        return null;
    }
}

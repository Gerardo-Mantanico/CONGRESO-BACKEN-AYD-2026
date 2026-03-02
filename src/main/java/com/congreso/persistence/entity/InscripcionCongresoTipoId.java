package com.congreso.persistence.entity;

import java.io.Serializable;

public class InscripcionCongresoTipoId implements Serializable {
    private Long inscripcionId;
    private String tipoParticipacion;

    public InscripcionCongresoTipoId() {}

    public InscripcionCongresoTipoId(Long inscripcionId, String tipoParticipacion) {
        this.inscripcionId = inscripcionId;
        this.tipoParticipacion = tipoParticipacion;
    }

    // equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InscripcionCongresoTipoId that = (InscripcionCongresoTipoId) o;
        if (inscripcionId != null ? !inscripcionId.equals(that.inscripcionId) : that.inscripcionId != null)
            return false;
        return tipoParticipacion != null ? tipoParticipacion.equals(that.tipoParticipacion) : that.tipoParticipacion == null;
    }

    @Override
    public int hashCode() {
        int result = inscripcionId != null ? inscripcionId.hashCode() : 0;
        result = 31 * result + (tipoParticipacion != null ? tipoParticipacion.hashCode() : 0);
        return result;
    }
}


package com.congreso.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "inscripcion_congreso_tipos")
@IdClass(InscripcionCongresoTipoId.class)
public class InscripcionCongresoTipoEntity {

    @Id
    @Column(name = "inscripcion_id")
    private Long inscripcionId;

    @Id
    @Column(name = "tipo_participacion")
    private String tipoParticipacion;
}

package com.congreso.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;
import java.time.Instant;

@Entity
@Data
@Table(name = "actividad")
public class ActividadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(columnDefinition = "text")
    private String descripcion;

    private String tipo; // PONENCIA or TALLER

    @Column(name = "hora_inicio")
    private OffsetDateTime horaInicio;

    @Column(name = "hora_fin")
    private OffsetDateTime horaFin;

    @Column(name = "capacidad_maxima")
    private Integer capacidadMaxima;

    @Column(name = "salon_id")
    private Long salonId;

    @Column(name = "congreso_id")
    private Long congresoId;

    @Column(name = "trabajo_origen_id")
    private Long trabajoOrigenId;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt = Instant.now();

    private Boolean activo = true;
}


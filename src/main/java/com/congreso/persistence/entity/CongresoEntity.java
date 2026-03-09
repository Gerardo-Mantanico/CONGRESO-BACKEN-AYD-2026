package com.congreso.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Data
@Table(name = "congreso")
public class CongresoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(columnDefinition = "text")
    private String descripcion;

    @Column(name = "fecha_inicio")
    private OffsetDateTime fechaInicio;

    @Column(name = "fecha_fin")
    private OffsetDateTime fechaFin;

    private String ubicacion;

    @Column(name = "precio_inscripcion")
    private BigDecimal precioInscripcion;

    @Column(name = "comision_porcentaje")
    private BigDecimal comisionPorcentaje;

    @Column(name = "foto_url", columnDefinition = "jsonb")
    private String fotoUrl; // almacenamos JSONB como String para simplicidad

    private Boolean activo = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "institucion_id", referencedColumnName = "id")
    private InstitucionEntity institucionId;

    @Column(name = "created_at")
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
}

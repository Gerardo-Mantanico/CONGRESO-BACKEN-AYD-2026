package com.congreso.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
@Table(name = "pago")
public class PagoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inscripcion_id")
    private Long inscripcionId;

    @Column(name = "monto_total")
    private BigDecimal montoTotal;

    @Column(name = "comision_porcentaje")
    private BigDecimal comisionPorcentaje;

    @Column(name = "monto_comision")
    private BigDecimal montoComision;

    @Column(name = "fecha_pago")
    private Instant fechaPago;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();
}

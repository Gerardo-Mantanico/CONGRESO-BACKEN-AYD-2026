package com.congreso.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Data
@Table(name = "configuracion_sistema")
public class ConfiguracionSistemaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "comision_porcentaje_default")
    private BigDecimal comision;
    @Column(insertable = false)
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}

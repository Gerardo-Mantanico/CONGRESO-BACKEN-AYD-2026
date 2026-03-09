package com.congreso.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Data
@Table(name = "cuenta_digital")
public class CuentaDigitalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false, unique = true)
    private Long usuarioId;

    @Column(name = "saldo")
    private BigDecimal saldo;

    @Size(max = 8)
    @Column(name = "moneda", length = 8)
    private String moneda = "GTQ";

    @Column(name = "activa", nullable = false)
    private Boolean activa = true;

    @Column(nullable = false)
    private String numeroCuenta;

    @Column(name = "created_at")
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt = OffsetDateTime.now();

}

package com.congreso.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "recarga_cuenta")
@Data
public class RecargaCuentaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "cuenta_digital_id", nullable = false)
    private Long cuentaDigitalId;

    @NotNull
    @Column(name = "monto", nullable = false)
    private BigDecimal monto;

    @Size(max = 8)
    @Column(name = "moneda", length = 8)
    private String moneda = "GTQ";

    @Size(max = 128)
    @Column(name = "referencia", length = 128)
    private String referencia;

    // campo 'estado' eliminado: la columna no existe en la tabla y no queremos validación en el entity
    // Si en el futuro se necesita, se puede añadir una migración y reintroducir este campo

    @Size(max = 64)
    @Column(name = "medio_pago", length = 64)
    private String medioPago;

    @Column(name = "operador_id")
    private Long operadorId;

    @Column(name = "fecha_recarga", nullable = false)
    private Instant fechaRecarga;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

}

package com.congreso.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.OffsetDateTime;

@Entity
@Data
@Table(name = "institucion")
public class InstitucionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private String direccion;
    private Boolean activo;
    private OffsetDateTime createdAt = OffsetDateTime.now();
    private OffsetDateTime updatedAt;
}

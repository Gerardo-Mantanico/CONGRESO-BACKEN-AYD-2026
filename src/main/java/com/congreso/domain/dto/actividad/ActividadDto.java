package com.congreso.domain.dto.actividad;

import java.time.OffsetDateTime;

public class ActividadDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private String tipo;
    private OffsetDateTime horaInicio;
    private OffsetDateTime horaFin;
    private Integer capacidadMaxima;
    private Long salonId;
    private Long congresoId;

    // getters and setters omitted for brevity (generate)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public OffsetDateTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(OffsetDateTime horaInicio) { this.horaInicio = horaInicio; }
    public OffsetDateTime getHoraFin() { return horaFin; }
    public void setHoraFin(OffsetDateTime horaFin) { this.horaFin = horaFin; }
    public Integer getCapacidadMaxima() { return capacidadMaxima; }
    public void setCapacidadMaxima(Integer capacidadMaxima) { this.capacidadMaxima = capacidadMaxima; }
    public Long getSalonId() { return salonId; }
    public void setSalonId(Long salonId) { this.salonId = salonId; }
    public Long getCongresoId() { return congresoId; }
    public void setCongresoId(Long congresoId) { this.congresoId = congresoId; }
}


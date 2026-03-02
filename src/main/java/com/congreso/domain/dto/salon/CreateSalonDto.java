package com.congreso.domain.dto.salon;

public class CreateSalonDto {
    private String nombre;
    private String ubicacion;
    private Integer capacidad;
    private String recursos;
    private Long congresoId;

    public CreateSalonDto() {}

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public Integer getCapacidad() { return capacidad; }
    public void setCapacidad(Integer capacidad) { this.capacidad = capacidad; }
    public String getRecursos() { return recursos; }
    public void setRecursos(String recursos) { this.recursos = recursos; }
    public Long getCongresoId() { return congresoId; }
    public void setCongresoId(Long congresoId) { this.congresoId = congresoId; }
}


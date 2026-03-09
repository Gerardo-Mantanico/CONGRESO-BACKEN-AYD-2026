package com.congreso.domain.dto.salon;

import lombok.Data;

@Data
public class SalonDto {
    private Long id;
    private String nombre;
    private String ubicacion;
    private Integer capacidad;
    private String recursos;
    private Long congresoId;
    private Long estadoId;
    private String estado;

}

package com.congreso.test;

import com.congreso.persistence.entity.*;
import com.congreso.domain.dto.congreso.CreateDtoCongreso;
import com.congreso.domain.dto.inscripcion.CreateInscripcionDto;
import com.congreso.domain.dto.salon.CreateSalonDto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class TestFixtures {
    public static CreateDtoCongreso createValidCongresoDto() {
        return new CreateDtoCongreso(
                "Titulo",
                "Descripcion",
                OffsetDateTime.of(2026,3,10,8,0,0,0, ZoneOffset.UTC),
                OffsetDateTime.of(2026,3,12,18,0,0,0, ZoneOffset.UTC),
                "Lugar",
                new BigDecimal("50.00"),
                new BigDecimal("5.00"),
                null,
                1L
        );
    }

    public static CongresoEntity createCongresoEntity() {
        CongresoEntity e = new CongresoEntity();
        e.setId(1L);
        e.setTitulo("Titulo");
        e.setDescripcion("Descripcion");
        e.setFechaInicio(OffsetDateTime.of(2026,3,10,8,0,0,0, ZoneOffset.UTC));
        e.setFechaFin(OffsetDateTime.of(2026,3,12,18,0,0,0, ZoneOffset.UTC));
        e.setPrecioInscripcion(new BigDecimal("50.00"));
        e.setComisionPorcentaje(new BigDecimal("5.00"));
        e.setActivo(true);
        return e;
    }

    public static CreateSalonDto createSalonDto() {
        CreateSalonDto d = new CreateSalonDto();
        d.setNombre("Salon A");
        d.setUbicacion("Piso 1");
        d.setCapacidad(100);
        d.setRecursos("Proyector");
        d.setCongresoId(1L);
        return d;
    }

    public static SalonEntity createSalonEntity() {
        SalonEntity s = new SalonEntity();
        s.setId(10L);
        s.setNombre("Salon A");
        s.setUbicacion("Piso 1");
        s.setCapacidad(100);
        s.setRecursos("Proyector");
        s.setCongresoId(1L);
        s.setActivo(true);
        return s;
    }

    public static CreateInscripcionDto createInscripcionDto() {
        return new CreateInscripcionDto(20L, 1L, List.of("ASISTENTE"));
    }
}


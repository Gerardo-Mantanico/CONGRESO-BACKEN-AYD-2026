package com.congreso.domain.dto.congreso;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record CreateDtoCongreso(
        @NotNull
        @Size(min = 1, max = 255)
        String titulo,

        String descripcion,

        @NotNull
        OffsetDateTime fechaInicio,

        @NotNull
        OffsetDateTime fechaFin,

        @Size(max = 255)
        String ubicacion,

        @NotNull
        @DecimalMin(value = "35.00")
        BigDecimal precioInscripcion,

        BigDecimal comisionPorcentaje,

        String fotoUrl,

        @NotNull
        Long institucionId
) {}


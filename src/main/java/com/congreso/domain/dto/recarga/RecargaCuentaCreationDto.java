package com.congreso.domain.dto.recarga;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class RecargaCuentaCreationDto {
    @NotNull
    private String cuentaDigitalId;

    @NotNull
    @Positive
    private BigDecimal monto;

    private String moneda;
    private String referencia;
    private String medioPago;
}


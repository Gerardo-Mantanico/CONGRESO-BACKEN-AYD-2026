package com.congreso.domain.dto.cuenta;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class CuentaDigitalCreationDto {

    private BigDecimal saldo;

    @Size(max = 8)
    private String moneda;

    public CuentaDigitalCreationDto() {
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }
}


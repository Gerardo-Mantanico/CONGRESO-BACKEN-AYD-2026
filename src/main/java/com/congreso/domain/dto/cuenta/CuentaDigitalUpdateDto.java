package com.congreso.domain.dto.cuenta;

import java.math.BigDecimal;

public class CuentaDigitalUpdateDto {
    private BigDecimal saldo;
    private Boolean activa;

    public CuentaDigitalUpdateDto() {
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }
}


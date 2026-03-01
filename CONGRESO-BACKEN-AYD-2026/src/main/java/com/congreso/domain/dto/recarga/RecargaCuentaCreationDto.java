package com.congreso.domain.dto.recarga;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class RecargaCuentaCreationDto {
    @NotNull
    private Long cuentaDigitalId;

    @NotNull
    @Positive
    private BigDecimal monto;

    private String moneda;
    private String referencia;
    private String medioPago;
    private Long operadorId;

    public RecargaCuentaCreationDto() {
    }

    public Long getCuentaDigitalId() {
        return cuentaDigitalId;
    }

    public void setCuentaDigitalId(Long cuentaDigitalId) {
        this.cuentaDigitalId = cuentaDigitalId;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    public Long getOperadorId() {
        return operadorId;
    }

    public void setOperadorId(Long operadorId) {
        this.operadorId = operadorId;
    }
}


package com.congreso.domain.repository;

import com.congreso.persistence.entity.CuentaDigitalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CuentaDigitalRepository extends JpaRepository<CuentaDigitalEntity, Long> {
    boolean existsByNumeroCuenta(String numeroCuenta);
    Optional<CuentaDigitalEntity> findByNumeroCuenta(String cuentaDigitalId);
    Optional<CuentaDigitalEntity> findByUsuarioId(Long usuarioId);
}


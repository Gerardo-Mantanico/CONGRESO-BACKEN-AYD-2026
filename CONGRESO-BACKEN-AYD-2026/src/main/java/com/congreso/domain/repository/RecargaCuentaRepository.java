package com.congreso.domain.repository;

import com.congreso.persistence.entity.RecargaCuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecargaCuentaRepository extends JpaRepository<RecargaCuentaEntity, Long> {
    List<RecargaCuentaEntity> findAllByCuentaDigitalIdOrderByFechaRecargaDesc(Long cuentaId);
    Optional<RecargaCuentaEntity> findByReferencia(String referencia);
}


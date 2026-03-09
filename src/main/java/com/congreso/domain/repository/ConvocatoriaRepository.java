package com.congreso.domain.repository;

import com.congreso.persistence.entity.ConvocatoriaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConvocatoriaRepository extends JpaRepository<ConvocatoriaEntity, Long> {
    // Buscar convocatorias activas por congreso (devuelve lista)
    Page<ConvocatoriaEntity> findByCongresoIdIdAndActivoTrue(Long congresoId, Pageable pageable);
    Page<ConvocatoriaEntity> findByActivoTrue(Pageable pageable);

}

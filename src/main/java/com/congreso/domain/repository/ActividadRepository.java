package com.congreso.domain.repository;

import com.congreso.persistence.entity.ActividadEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActividadRepository extends JpaRepository<ActividadEntity, Long> {
    Page<ActividadEntity> findByCongresoIdIdAndActivoTrue(Long congresoId, Pageable pageable);
    Page<ActividadEntity> findByConvocatoriaIdIdAndActivoTrue(Long convocatoriaId, Pageable pageable);
    Page<ActividadEntity> findByActivoTrueAndUser_Id(Pageable pageable, Long id);
}


package com.congreso.domain.repository;

import com.congreso.persistence.entity.SalonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SalonRepository extends JpaRepository<SalonEntity, Long> {
    Optional<SalonEntity> findByIdAndCongresoId(Long id, Long congresoId);
    boolean existsByNombreAndCongresoId(String nombre, Long congresoId);
    List<SalonEntity> findByCongresoIdAndActivoTrue(Long congresoId);
}

package com.congreso.domain.repository;

import com.congreso.persistence.entity.InstitucionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitucionRepository extends JpaRepository<InstitucionEntity, Long> {
}

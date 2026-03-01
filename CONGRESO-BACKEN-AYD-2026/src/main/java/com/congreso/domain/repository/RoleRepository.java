package com.congreso.domain.repository;

import com.congreso.persistence.entity.RoleEntity;
import com.congreso.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    @Query("SELECT role FROM UserRoleEntity userRole INNER JOIN userRole.role role WHERE userRole.user = :user")
    Set<RoleEntity> findAllRolesByUserId(@Param("user") UserEntity userEntity);

    // findAll() and findById are inherited from JpaRepository
    // Helper methods
    Optional<RoleEntity> findByName(String name);
    boolean existsByName(String name);


package com.congreso.domain.repository;

import com.congreso.persistence.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.userRoles ur LEFT JOIN FETCH ur.role WHERE u.email = :email AND u.active = true")
    Optional<UserEntity> findFirstByEmailAndActiveTrue(@Param("email") String email);

    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.userRoles ur LEFT JOIN FETCH ur.role WHERE u.email = :email")
    Optional<UserEntity> findByEmail(@Param("email") String email);

    Page<UserEntity> findAllByActiveTrue(Pageable pageable);

    @Query("SELECT u FROM UserEntity u WHERE u.active = true AND (:dpi IS NULL OR u.dpi LIKE %:dpi%) AND (:name IS NULL OR LOWER(CONCAT(u.firstname, ' ', u.lastname)) LIKE LOWER(CONCAT('%', :name, '%'))) ORDER BY u.id")
    List<UserEntity> findByDpiOrName(@Param("dpi") String dpi, @Param("name") String name);

}

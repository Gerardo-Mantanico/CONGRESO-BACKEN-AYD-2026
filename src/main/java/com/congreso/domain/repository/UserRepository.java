package com.congreso.domain.repository;

import com.congreso.persistence.entity.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(@NotBlank @Email String email);

    Optional<UserEntity> findFirstByEmailAndActiveTrue(String email);

    Page<UserEntity> findAllByActiveTrue(Pageable pageable);

    List<UserEntity> findByDpiOrLastname(String dpi, String name);
}

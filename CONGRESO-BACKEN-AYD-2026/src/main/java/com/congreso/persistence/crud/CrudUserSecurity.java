package com.congreso.persistence.crud;

import com.congreso.persistence.entity.UserSecurityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface CrudUserSecurity extends JpaRepository<UserSecurityEntity, Long> {

    @Query("SELECT us FROM UserSecurityEntity us WHERE us.user = :user AND us.codeType = :codeType AND us.status = com.congreso.domain.enums.SecurityCodeStatus.ACTIVE")
    Optional<UserSecurityEntity> findCodeByCodeAndTypeAndStatus(@Param("user") com.congreso.persistence.entity.UserEntity user, @Param("codeType") com.congreso.domain.enums.SecurityCodeType codeType, @Param("code") String code);

    // Inactivate previous codes for user and type
    @Query("UPDATE UserSecurityEntity us SET us.status = com.congreso.domain.enums.SecurityCodeStatus.INACTIVE WHERE us.user = :user AND us.codeType = :codeType AND us.status = com.congreso.domain.enums.SecurityCodeStatus.ACTIVE")
    void inactivePreviousByUserAndCodeType(@Param("user") com.congreso.persistence.entity.UserEntity user, @Param("codeType") com.congreso.domain.enums.SecurityCodeType codeType);
}


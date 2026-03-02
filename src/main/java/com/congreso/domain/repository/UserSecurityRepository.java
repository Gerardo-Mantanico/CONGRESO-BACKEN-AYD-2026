package com.congreso.domain.repository;

import com.congreso.domain.enums.SecurityCodeType;
import com.congreso.persistence.entity.UserEntity;
import com.congreso.persistence.entity.UserSecurityEntity;

import java.util.Optional;

public interface UserSecurityRepository {
    Optional<UserSecurityEntity> findCodeByCodeAndTypeAndStatus(UserEntity userEntity, String code, SecurityCodeType securityCodeType);
    void inactivePreviousByUserAndCodeType(UserEntity userEntity, SecurityCodeType securityCodeType);
    void save(UserSecurityEntity userSecurityEntity);
}

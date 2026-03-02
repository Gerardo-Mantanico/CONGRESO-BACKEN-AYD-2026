package com.congreso.persistence;

import com.congreso.domain.enums.SecurityCodeStatus;
import com.congreso.domain.enums.SecurityCodeType;
import com.congreso.domain.repository.UserSecurityRepository;
import com.congreso.persistence.crud.CrudUserSecurity;
import com.congreso.persistence.entity.UserEntity;
import com.congreso.persistence.entity.UserSecurityEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class UserSecurityEntityRepository implements UserSecurityRepository {

    private final CrudUserSecurity crudUserSecurity;

    public UserSecurityEntityRepository(CrudUserSecurity crudUserSecurity) {
        this.crudUserSecurity = crudUserSecurity;
    }

    @Override
    public Optional<UserSecurityEntity> findCodeByCodeAndTypeAndStatus(UserEntity userEntity, String code, SecurityCodeType securityCodeType) {
        // Finder needs to match code and type and ACTIVE status. The Crud method with @Query above is limited; implement manually.
        return this.crudUserSecurity.findAll().stream()
                .filter(us -> us.getUser().equals(userEntity) && us.getCodeType() == securityCodeType && us.getStatus() == SecurityCodeStatus.ACTIVE && this.crudUserSecurity.getOne(us.getId()).getCode().equals(code))
                .findFirst();
    }

    @Override
    @Transactional
    public void inactivePreviousByUserAndCodeType(UserEntity userEntity, SecurityCodeType securityCodeType) {
        this.crudUserSecurity.findAll().stream()
                .filter(us -> us.getUser().equals(userEntity) && us.getCodeType() == securityCodeType && us.getStatus() == SecurityCodeStatus.ACTIVE)
                .forEach(us -> {
                    us.setStatus(SecurityCodeStatus.INACTIVE);
                    this.crudUserSecurity.save(us);
                });
    }

    @Override
    public void save(UserSecurityEntity userSecurityEntity) {
        this.crudUserSecurity.save(userSecurityEntity);
    }
}


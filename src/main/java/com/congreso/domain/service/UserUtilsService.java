package com.congreso.domain.service;

import com.congreso.domain.enums.Role;
import com.congreso.persistence.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserUtilsService {
    public UserEntity getCurrent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserEntity) authentication.getPrincipal();
    }

    public boolean hasAnyAuthority(Set<Role> roles) {
        return hasAnyAuthority(this.getCurrent(), roles);
    }

    public boolean hasAuthority(Role role) {
        return hasAuthority(this.getCurrent(), role);
    }

    public boolean hasAnyAuthority(UserEntity userEntity, Set<Role> roles) {
        Set<String> roleNames = roles.stream().map(Role::name).collect(Collectors.toSet());
        return userEntity.getAuthorities().stream().anyMatch(a -> roleNames.contains(a.getAuthority()));
    }

    public boolean hasAuthority(UserEntity userEntity, Role role) {
        return hasAnyAuthority(userEntity, Set.of(role));
    }
}

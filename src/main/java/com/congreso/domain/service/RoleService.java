package com.congreso.domain.service;

import com.congreso.domain.dto.role.RoleDto;
import com.congreso.domain.repository.RoleRepository;
import com.congreso.persistence.entity.RoleEntity;
import com.congreso.persistence.entity.UserEntity;
import com.congreso.persistence.mapper.RoleMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public Set<RoleEntity> findRolesByUser(UserEntity user) {
        return roleRepository.findAllRolesByUserId(user);
    }

    public List<RoleDto> findAllRoles() {
        return this.roleMapper.toDto(this.roleRepository.findAll());
    }
}

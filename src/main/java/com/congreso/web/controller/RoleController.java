package com.congreso.web.controller;

import com.congreso.domain.dto.role.RoleDto;
import com.congreso.domain.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/roles")
@RestController
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    @GetMapping
    public ResponseEntity<List<RoleDto>> findAllRoles() {
        return ResponseEntity.ok(this.roleService.findAllRoles());
    }
}

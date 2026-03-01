package com.congreso.web.controller;

import com.congreso.persistence.entity.ConfiguracionSistemaEntity;

import com.congreso.domain.service.ConfiguracionSistemaService;
// ...existing code...
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/configuracion-sistema")
@Tag(name = "Configuración del Sistema")
public class ConfiguracionSistemaController {

    private final ConfiguracionSistemaService configuracionSistemaService;
    public ConfiguracionSistemaController(ConfiguracionSistemaService configuracionSistemaService) {
        this.configuracionSistemaService = configuracionSistemaService;
    }


    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')") // Solo los usuarios con el rol ADMIN pueden acceder a esta ruta
    @Operation(summary = "Obtener la configuración del sistema")
    public ResponseEntity<ConfiguracionSistemaEntity> getConfiguracionSistema() {
        ConfiguracionSistemaEntity configuracion = configuracionSistemaService.getConfiguracionSistema();
        if (configuracion != null) {
            return ResponseEntity.ok(configuracion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

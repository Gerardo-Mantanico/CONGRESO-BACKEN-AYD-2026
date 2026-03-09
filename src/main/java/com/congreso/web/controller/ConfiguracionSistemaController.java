package com.congreso.web.controller;

import com.congreso.persistence.entity.ConfiguracionSistemaEntity;

import com.congreso.domain.service.ConfiguracionSistemaService;
import com.congreso.domain.dto.ConfiguracionSistema.updateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar la configuración del sistema")
    public ResponseEntity<ConfiguracionSistemaEntity> updateConfiguracionSistema(@PathVariable Long id,
                                                                                 @RequestBody @Valid updateDto dto) {
        ConfiguracionSistemaEntity updated = configuracionSistemaService.update(id, dto);
        return ResponseEntity.ok(updated);
    }
}

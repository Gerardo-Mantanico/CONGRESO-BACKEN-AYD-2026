package com.congreso.domain.dto.institucion;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record CreateDtoInstitucion(
        @NotNull
        @Size(min = 1, max = 255)
        String nombre,
        @NotNull
        String descripcion,
        @NotNull
        @Size(min = 1, max = 255)
        String direccion,
        // URLs opcionales de fotos
        List<String> fotosUrl
) {
}

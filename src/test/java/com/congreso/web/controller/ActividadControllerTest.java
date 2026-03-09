package com.congreso.web.controller;

import com.congreso.domain.dto.actividad.ActividadDto;
import com.congreso.domain.dto.actividad.CreateActividadDto;
import com.congreso.domain.dto.congreso.CongresoResponseDto;
import com.congreso.domain.dto.convocatoria.ConvocatoriaDto;
import com.congreso.domain.dto.institucion.InstitucionResponseDto;
import com.congreso.domain.dto.user.UserDto;
import com.congreso.domain.service.ActividadService;
import com.congreso.domain.enums.Estados;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ActividadController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ActividadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActividadService actividadService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void create_returnsCreated() throws Exception {
        CreateActividadDto req = new CreateActividadDto(
                "Nombre",
                "desc",
                "PONENCIA",
                OffsetDateTime.now().plusDays(1),
                OffsetDateTime.now().plusDays(1).plusHours(2),
                null,
                1L,
                1L,
                null
        );

        InstitucionResponseDto inst = new InstitucionResponseDto(1L, "I", "D", "U", null, true, null, null);
        CongresoResponseDto congreso = new CongresoResponseDto(1L, "T", "D", req.horaInicio().minusDays(1), req.horaFin().plusDays(1), "U", null, null, null, true, inst, null, null);
        ConvocatoriaDto convoc = new ConvocatoriaDto(1L, "N", "D", req.horaInicio().minusDays(2), req.horaInicio().minusDays(1), congreso, "ACT", null, null);
        UserDto user = new UserDto(1L, "f", "l", "e@e", "p", "dpi", null, null, true, null, false, null);

        ActividadDto dto = new ActividadDto(1L, req.nombre(), req.descripcion(), req.tipo(), req.horaInicio(), req.horaFin(), req.capacidadMaxima(), congreso, convoc, user, req.archivoUrl(), Estados.ACTIVO, null, null);
        when(actividadService.create(req)).thenReturn(dto);

        mockMvc.perform(post("/actividades").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }
}

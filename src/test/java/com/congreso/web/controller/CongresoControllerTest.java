package com.congreso.web.controller;

import com.congreso.domain.dto.congreso.CreateDtoCongreso;
import com.congreso.domain.dto.congreso.CongresoResponseDto;
import com.congreso.domain.service.CongresoService;
import com.congreso.test.TestFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CongresoController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CongresoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CongresoService congresoService;

    @MockBean
    private com.congreso.domain.service.JwtService jwtService;

    @MockBean
    private org.springframework.security.core.userdetails.UserDetailsService userDetailsService;

    @MockBean
    private org.springframework.web.servlet.HandlerExceptionResolver handlerExceptionResolver;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void list_returnsOk() throws Exception {
        var c = TestFixtures.createCongresoEntity();
        var dto = new CongresoResponseDto(c.getId(), c.getTitulo(), c.getDescripcion(), c.getFechaInicio(), c.getFechaFin(), c.getUbicacion(), c.getPrecioInscripcion(), c.getComisionPorcentaje(), c.getFotoUrl(), c.getActivo(), c.getInstitucionId(), c.getCreatedAt(), c.getUpdatedAt());
        when(congresoService.list()).thenReturn(List.of(dto));

        mockMvc.perform(get("/congresos").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void create_returnsOk() throws Exception {
        CreateDtoCongreso req = TestFixtures.createValidCongresoDto();
        var entity = TestFixtures.createCongresoEntity();
        var dto = new CongresoResponseDto(entity.getId(), entity.getTitulo(), entity.getDescripcion(), entity.getFechaInicio(), entity.getFechaFin(), entity.getUbicacion(), entity.getPrecioInscripcion(), entity.getComisionPorcentaje(), entity.getFotoUrl(), entity.getActivo(), entity.getInstitucionId(), entity.getCreatedAt(), entity.getUpdatedAt());
        when(congresoService.create(req)).thenReturn(dto);

        mockMvc.perform(post("/congresos").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value(entity.getTitulo()));
    }
}

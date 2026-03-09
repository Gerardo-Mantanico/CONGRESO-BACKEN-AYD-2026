package com.congreso.web.controller;

import com.congreso.domain.dto.institucion.CreateDtoInstitucion;
import com.congreso.domain.dto.institucion.InstitucionResponseDto;
import com.congreso.domain.service.InstitucionService;
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

@WebMvcTest(InstitucionController.class)
@AutoConfigureMockMvc(addFilters = false)
public class InstitucionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InstitucionService institucionService;

    @MockBean
    private com.congreso.domain.service.JwtService jwtService;

    @MockBean
    private org.springframework.security.core.userdetails.UserDetailsService userDetailsService;

    @MockBean
    private org.springframework.web.servlet.HandlerExceptionResolver handlerExceptionResolver;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void create_returnsOk() throws Exception {
        CreateDtoInstitucion req = new CreateDtoInstitucion("Nombre","Desc corta","Direccion", java.util.List.of());
        InstitucionResponseDto resDto = new InstitucionResponseDto(1L, "Nombre", "Desc corta", "Direccion", java.util.List.of(), true, OffsetDateTime.now(), OffsetDateTime.now());
        when(institucionService.create(req)).thenReturn(resDto);

        mockMvc.perform(post("/instituciones").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Nombre"));
    }
}

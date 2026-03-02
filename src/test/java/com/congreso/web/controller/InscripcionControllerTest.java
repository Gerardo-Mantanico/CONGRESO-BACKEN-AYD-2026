package com.congreso.web.controller;

import com.congreso.domain.dto.inscripcion.CreateInscripcionDto;
import com.congreso.domain.dto.inscripcion.InscripcionResponseDto;
import com.congreso.domain.service.InscripcionCongresoService;
import com.congreso.test.TestFixtures;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InscripcionController.class)
@AutoConfigureMockMvc(addFilters = false)
public class InscripcionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InscripcionCongresoService inscripcionService;

    private final ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private com.congreso.domain.service.JwtService jwtService;

    @MockBean
    private org.springframework.security.core.userdetails.UserDetailsService userDetailsService;

    @MockBean
    private org.springframework.web.servlet.HandlerExceptionResolver handlerExceptionResolver;

    @Test
    void create_returnsOk() throws Exception {
        CreateInscripcionDto req = TestFixtures.createInscripcionDto();
        InscripcionResponseDto res = new InscripcionResponseDto(1L, req.usuarioId(), req.congresoId(), null, java.util.List.of("ASISTENTE"), 5L);
        when(inscripcionService.create(req)).thenReturn(res);

        mockMvc.perform(post("/inscripciones").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
}

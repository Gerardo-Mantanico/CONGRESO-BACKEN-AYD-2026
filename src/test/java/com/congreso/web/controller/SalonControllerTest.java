package com.congreso.web.controller;

import com.congreso.domain.dto.salon.CreateSalonDto;
import com.congreso.domain.dto.salon.SalonDto;
import com.congreso.domain.service.SalonService;
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

@WebMvcTest(SalonController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SalonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SalonService salonService;

    @MockBean
    private com.congreso.domain.service.JwtService jwtService;

    @MockBean
    private org.springframework.security.core.userdetails.UserDetailsService userDetailsService;

    @MockBean
    private org.springframework.web.servlet.HandlerExceptionResolver handlerExceptionResolver;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void create_returnsOk() throws Exception {
        CreateSalonDto req = TestFixtures.createSalonDto();
        var entity = TestFixtures.createSalonEntity();
        SalonDto dto = new SalonDto();
        dto.setId(entity.getId()); dto.setNombre(entity.getNombre()); dto.setUbicacion(entity.getUbicacion()); dto.setCapacidad(entity.getCapacidad()); dto.setRecursos(entity.getRecursos()); dto.setCongresoId(entity.getCongresoId());

        when(salonService.create(req)).thenReturn(dto);

        mockMvc.perform(post("/salones").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(entity.getId()));
    }
}

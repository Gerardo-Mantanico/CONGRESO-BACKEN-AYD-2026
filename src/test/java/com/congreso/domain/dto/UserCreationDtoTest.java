package com.congreso.domain.dto;

import com.congreso.domain.dto.user.UserCreationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserCreationDtoTest {

    private final Validator validator;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserCreationDtoTest() {
        // Construir el Validator usando try-with-resources para evitar la advertencia
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void validationFails_whenRequiredFieldsMissing() {
        UserCreationDto dto = new UserCreationDto();
        Set<ConstraintViolation<UserCreationDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void serializesAndDeserializes() throws Exception {
        UserCreationDto dto = new UserCreationDto("Juan","Perez","juan@example.com","12345678","pass","1000000000000");
        String json = objectMapper.writeValueAsString(dto);
        UserCreationDto read = objectMapper.readValue(json, UserCreationDto.class);
        assertEquals(dto.getFirstname(), read.getFirstname());
        assertEquals(dto.getLastname(), read.getLastname());
        assertEquals(dto.getEmail(), read.getEmail());
    }
}

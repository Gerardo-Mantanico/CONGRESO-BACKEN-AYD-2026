package com.congreso.domain.service;

import com.congreso.domain.dto.user.UserCreationDto;
import com.congreso.domain.dto.user.UserDto;
import com.congreso.domain.exception.GeneralException;
import com.congreso.domain.repository.RoleRepository;
import com.congreso.domain.repository.UserRepository;
import com.congreso.persistence.entity.RoleEntity;
import com.congreso.persistence.entity.UserEntity;
import com.congreso.persistence.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void registerPaciente_happyPath_returnsDto() {
        UserCreationDto dto = new UserCreationDto();
        dto.setFirstname("Juan");
        dto.setLastname("Perez");
        dto.setEmail("juan@example.com");
        dto.setPhoneNumber("12345678");
        dto.setPassword("password");
        dto.setDpi("1000000000000");
        dto.setRoleId(1L);

        when(userRepository.findFirstByEmailAndActiveTrue(dto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn("hashed");

        RoleEntity role = new RoleEntity();
        role.setId(1L);
        role.setName("CLIENTE");
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        UserEntity entity = new UserEntity();
        entity.setId(10L);
        entity.setFirstname(dto.getFirstname());
        entity.setLastname(dto.getLastname());
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setDpi(dto.getDpi());
        entity.setPassword("hashed");

        when(userMapper.toEntity(any(UserCreationDto.class))).thenReturn(entity);
        when(userRepository.save(any(UserEntity.class))).thenReturn(entity);

        UserDto expectedDto = new UserDto(entity.getId(), entity.getFirstname(), entity.getLastname(), entity.getEmail(), entity.getPhoneNumber(), entity.getDpi(), null, null, entity.getActive(), null, entity.getUse2fa(), null);
        when(userMapper.toDto(entity)).thenReturn(expectedDto);

        UserDto result = userService.registerPaciente(dto);

        assertNotNull(result);
        assertEquals(expectedDto.id(), result.id());
        verify(userRepository).save(any(UserEntity.class));
        verify(emailService).sendEmailAsync(eq(dto.getEmail()), anyString(), anyString());
    }

    @Test
    void registerPaciente_whenEmailExists_throws() {
        UserCreationDto dto = new UserCreationDto();
        dto.setFirstname("Juan");
        dto.setLastname("Perez");
        dto.setEmail("juan@example.com");
        dto.setPhoneNumber("12345678");
        dto.setPassword("password");
        dto.setDpi("1000000000000");
        dto.setRoleId(1L);

        UserEntity existing = new UserEntity();
        existing.setEmail(dto.getEmail());

        when(userRepository.findFirstByEmailAndActiveTrue(dto.getEmail())).thenReturn(Optional.of(existing));

        assertThrows(GeneralException.class, () -> userService.registerPaciente(dto));

        verify(userRepository, never()).save(any());
    }

}

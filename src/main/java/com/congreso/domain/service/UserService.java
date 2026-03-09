package com.congreso.domain.service;

import com.congreso.domain.dto.user.UserCreationDto;
import com.congreso.domain.dto.user.UserDto;
import com.congreso.domain.dto.user.UserUpdateDto;
import com.congreso.domain.exception.GeneralException;
import com.congreso.domain.repository.*;
import com.congreso.persistence.entity.*;
import com.congreso.persistence.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final CuentaDigitalRepository cuentaDigitalRepository; // inyectado


    @Transactional
    public UserDto registerPaciente(UserCreationDto userCreationDto) {
        // Validar que el correo no exista ya
        if (this.userRepository.findFirstByEmailAndActiveTrue(userCreationDto.getEmail()).isPresent()) {
            throw new GeneralException("email-already-registered", "El correo electrónico ya está registrado.");
        }

        // Hashear contraseña en el DTO para que el mapper la copie ya hasheada
        String hashedPassword = passwordEncoder.encode(userCreationDto.getPassword());
        userCreationDto.setPassword(hashedPassword);

        Long roleId = userCreationDto.getRoleId();
        RoleEntity role = this.roleRepository.findById(roleId)
                .orElseThrow(() -> new GeneralException("role-not-found", "Rol no encontrado"));
        UserEntity userEntity = userMapper.toEntity(userCreationDto);
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUser(userEntity);
        userRoleEntity.setRole(role);
        userEntity.setUserRole(userRoleEntity);
        userEntity.setUse2fa(false);
        userEntity = userRepository.save(userEntity);
        userRoleEntity.setUser(userEntity);

        // Leer la cuenta digital creada por el trigger (el trigger se ejecuta durante el INSERT)
        String numeroCuenta = cuentaDigitalRepository.findByUsuarioId(userEntity.getId())
                .map(CuentaDigitalEntity::getNumeroCuenta)
                .orElse(null);

        String emailBody = "Hola " + userEntity.getFirstname() + ",\n\n" +
                "Gracias por registrarte en PIFirm. Estamos encantados de tenerte con nosotros.\n\n";

        if (numeroCuenta != null) {
            emailBody += "Tu cuenta digital ha sido creada con número: " + numeroCuenta + " y saldo inicial de Q50.00\n\n";
        } else {
            emailBody += "Tu cuenta digital será creada en breve.\n\n";
        }

        emailBody += "Saludos cordiales,\nEl equipo de PIFirm";

        emailService.sendEmailAsync(userCreationDto.getEmail(), "Bienvenido a PIFirm", emailBody);

        return this.userMapper.toDto(userEntity);
    }

    @Transactional
    public UserDto update(UserUpdateDto userUpdateDto, Long userId) {
        UserEntity userEntity = this.userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException("user-not-found", "Usuario no encontrado"));

        if (userUpdateDto.passwordNotEmpty())
            userEntity.setPassword(this.passwordEncoder.encode(userUpdateDto.getPassword()));
        if (userUpdateDto.firstnameNotEmpty())
            userEntity.setFirstname(userUpdateDto.getFirstname());
        if (userUpdateDto.lastnameNotEmpty())
            userEntity.setLastname(userUpdateDto.getLastname());
        if (userUpdateDto.phoneNumberNotEmpty())
            userEntity.setPhoneNumber(userUpdateDto.getPhoneNumber());
        if (userUpdateDto.getFotoUrl() != null) {
            userEntity.setFotoUrl(userUpdateDto.getFotoUrl());
        }
        if (userUpdateDto.getRoleId() != null) {
            RoleEntity role = this.roleRepository.findById(userUpdateDto.getRoleId())
                    .orElseThrow(() -> new GeneralException("role-not-found", "Rol no encontrado"));
            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setUser(userEntity);
            userRoleEntity.setRole(role);
            userEntity.setUserRole(userRoleEntity);
        }

        if (userUpdateDto.getUse2fa() != null) {
            userEntity.setUse2fa(userUpdateDto.getUse2fa());
        }

        if (userUpdateDto.getIsActive() != null) {
            userEntity.setActive(userUpdateDto.getIsActive());
        }

        this.userRepository.save(userEntity);

        return this.userMapper.toDto(userEntity);
    }

    @Transactional
    public Page<UserDto> allUsers(Pageable pageable) {
        return userRepository.findAllByActiveTrue(pageable).map(userMapper::toDto);
    }
    @Transactional
    public UserDto getById(Long userId) {
        UserEntity userEntity = this.userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException("user-not-found", "Usuario no encontrado"));
        return this.userMapper.toDto(userEntity);
    }

    @Transactional
    public void delete(Long userId) {
        UserEntity userEntity = this.userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException("user-not-found", "Usuario no encontrado"));
        userEntity.setActive(false);
        this.userRepository.save(userEntity);
    }

    public UserDto add(UserCreationDto userCreationDto) {
        // Validar que el correo no exista ya
        if (this.userRepository.findFirstByEmailAndActiveTrue(userCreationDto.getEmail()).isPresent()) {
            throw new GeneralException("email-already-registered", "El correo electrónico ya está registrado.");
        }

        LocalDate fecha = LocalDate.now();
        userCreationDto.setPassword( fecha + "user");
        String hashedPassword = passwordEncoder.encode(userCreationDto.getPassword());
        userCreationDto.setPassword(hashedPassword);

        Long roleId = userCreationDto.getRoleId();
        RoleEntity role = this.roleRepository.findById(roleId)
                .orElseThrow(() -> new GeneralException("role-not-found", "Rol no encontrado"));
        UserEntity userEntity = userMapper.toEntity(userCreationDto);
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUser(userEntity);
        userRoleEntity.setRole(role);
        userEntity.setUserRole(userRoleEntity);
        userEntity.setUse2fa(false);
        userEntity = userRepository.save(userEntity);
        return this.userMapper.toDto(userEntity);
    }


    @Transactional
    public List<UserDto> searchByDpiOrName(String dpi, String name) {
        List<UserEntity> list = this.userRepository.findByDpiOrLastname(dpi, name);
        return userMapper.toDto(list);
    }



}

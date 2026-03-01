package com.congreso.domain.service;

import com.congreso.domain.dto.auth.ChangePasswordReqDto;
import com.congreso.domain.dto.login.LoginDto;
import com.congreso.domain.dto.auth.RecoverPasswordDto;
import com.congreso.domain.dto.auth.VerifyCodeDto;
import com.congreso.domain.enums.SecurityCodeStatus;
import com.congreso.domain.enums.SecurityCodeType;
import com.congreso.domain.exception.GeneralException;
import com.congreso.domain.repository.UserRepository;
import com.congreso.domain.repository.UserSecurityRepository;
import com.congreso.domain.utils.CodeGenerator;
import com.congreso.persistence.entity.UserEntity;
import com.congreso.persistence.entity.UserSecurityEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserSecurityRepository userSecurityRepository;
    private final EmailService emailService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserSecurityRepository userSecurityRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userSecurityRepository = userSecurityRepository;
        this.emailService = emailService;
    }

    public UserSecurityEntity generateCode(
            UserEntity userEntity,
            SecurityCodeType securityCodeType,
            Integer seconds
    ) {
        UserSecurityEntity us = new UserSecurityEntity();
        us.setCodeType(securityCodeType);
        us.setUser(userEntity);

        // Set code - length 6
        us.setCode(CodeGenerator.generateNumericCode(6));

        final String codeToSend = us.getCode();

        log.info("Code: {}", codeToSend);
        // Set expiration - 2 minutes
        us.setCodeExpiration(Instant.now().plusSeconds(seconds));

        // Set encoded code
        us.setCode(this.passwordEncoder.encode(codeToSend));
        // Set active
        us.setStatus(SecurityCodeStatus.ACTIVE);
        this.userSecurityRepository.save(us);
        emailService.sendEmailAsync(
                userEntity.getEmail(),
                securityCodeType == SecurityCodeType.LOGIN_2FA
                        ? "Código para iniciar sesión"
                        : "Código para recuperar contraseña",
                "Tú código de seguridad es " + codeToSend
        );
        return us;
    }

    @Transactional
    public UserEntity authenticate(LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.email(),
                        loginDto.password()
                )
        );

        UserEntity userEntity = userRepository.findByEmail(loginDto.email())
                .orElseThrow(() -> new GeneralException("user-not-found", "Usuario no encontrado"));

        if (userEntity.getUse2fa()) {
            this.userSecurityRepository.inactivePreviousByUserAndCodeType(userEntity, SecurityCodeType.LOGIN_2FA);
            this.generateCode(userEntity, SecurityCodeType.LOGIN_2FA, 120);
        }

        return userEntity;
    }

    public UserEntity verifyCode(VerifyCodeDto verifyCodeDto, SecurityCodeType securityCodeType) {
        UserEntity userEntity = userRepository.findByEmail(verifyCodeDto.email())
                .orElseThrow(
                        () -> new GeneralException("user-not-found", "Usuario no encontrado", HttpStatus.UNAUTHORIZED)
                );

        // Validate if uses 2fa
        if (SecurityCodeType.LOGIN_2FA.equals(securityCodeType) && !userEntity.getUse2fa()) {
            throw new GeneralException("user-not-found", "Para usar la autenticación de 2 pasos, debes activarla en tus preferencias");
        }

        UserSecurityEntity us = this.userSecurityRepository.findCodeByCodeAndTypeAndStatus(userEntity, verifyCodeDto.code(), securityCodeType).orElseThrow(
                () -> new GeneralException("invalid-code", "Código inválido", HttpStatus.UNAUTHORIZED)
        );

        if (us.getCodeExpiration().isBefore(Instant.now())) {
            throw new GeneralException("expired-code", "El código ha expirado");
        }

        us.setStatus(SecurityCodeStatus.USED);

        this.userSecurityRepository.save(us);

        return userEntity;
    }


    public Optional<UserSecurityEntity> recoverPassword(RecoverPasswordDto recoverPasswordDto) {
        UserEntity userEntity = userRepository.findByEmail(recoverPasswordDto.email())
                .orElseThrow(
                        () -> new GeneralException("user-not-found", "Usuario no encontrado", HttpStatus.UNAUTHORIZED)
                );

        return Optional.of(this.generateCode(userEntity, SecurityCodeType.RECOVERY_PASSWORD, 60 * 5));
    }


    public UserEntity changePassword(ChangePasswordReqDto changePasswordDto) {
        UserEntity userEntity = this.verifyCode(
                new VerifyCodeDto(changePasswordDto.email(), changePasswordDto.code()),
                SecurityCodeType.RECOVERY_PASSWORD
        );

        if (passwordEncoder.matches(changePasswordDto.newPassword(), userEntity.getPassword())) {
            throw new GeneralException("invalid-new-password", "La contraseña no debe ser la misma que la anterior");
        }

        userEntity.setPassword(this.passwordEncoder.encode(changePasswordDto.newPassword()));
        this.userRepository.save(userEntity);

        return userEntity;
    }
}

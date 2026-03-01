package com.congreso.web.controller;

import com.congreso.domain.dto.auth.ChangePasswordReqDto;
import com.congreso.domain.dto.auth.RecoverPasswordDto;
import com.congreso.domain.dto.auth.VerifyCodeDto;
import com.congreso.domain.dto.login.LoginDto;
import com.congreso.domain.dto.login.LoginResponseDto;
import com.congreso.domain.dto.user.UserCreationDto;
import com.congreso.domain.dto.user.UserDto;
import com.congreso.domain.enums.Role;
import com.congreso.domain.enums.SecurityCodeType;
import com.congreso.domain.service.AuthService;
import com.congreso.domain.service.JwtService;
import com.congreso.domain.service.UserService;
import com.congreso.persistence.entity.UserEntity;
import com.congreso.persistence.mapper.UserMapper;
import com.congreso.web.response.GenericResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Este endpoint es para manejar la autenticación.")
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final UserService userService;

    public AuthController(AuthService authService, JwtService jwtService, UserMapper userMapper, UserService userService) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @Operation(summary = "Registro de un cliente final", description = "Registra un nuevo usuario con rol de cliente final. No se requiere enviar el roleId, ya que por defecto será un cliente final.")
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody @Valid UserCreationDto userCreationDto) {
        userCreationDto.setRoleId(Role.PARTICIPANTE.getId());
        return ResponseEntity.ok(this.userService.registerPaciente(userCreationDto));
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Ejemplo de credenciales para login",
        required = true,
        content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                value = "{\"email\": \"webbank404@gmail.com\", \"password\": \"P@ssword1\"}"
            )
        )
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginDto loginDto) {
        UserEntity authenticatedUser = authService.authenticate(loginDto);
        if (authenticatedUser == null) {
            return ResponseEntity.badRequest().body(new LoginResponseDto(null, null));
        }
        var use2fa = authenticatedUser.getUse2fa();
        var response = ResponseEntity.status(HttpStatus.OK);


        if (!use2fa) {
            String jwtToken = jwtService.generateToken(authenticatedUser);
            response.header("Authorization", jwtToken);
        }

        return response.body(new LoginResponseDto(use2fa, use2fa ? null : userMapper.toDto(authenticatedUser)));
    }

    @PostMapping("/verify-code")
    public ResponseEntity<UserDto> verifyCode(@RequestBody @Valid VerifyCodeDto verifyCodeDto) {
        UserEntity authenticatedUser = authService.verifyCode(verifyCodeDto, SecurityCodeType.LOGIN_2FA);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Authorization", jwtToken)
                .body(userMapper.toDto(authenticatedUser));
    }

    @PostMapping("/recover-password")
    public ResponseEntity<GenericResponse> recoverPassword(@RequestBody @Valid RecoverPasswordDto recoverPasswordDto) {
        if (this.authService.recoverPassword(recoverPasswordDto).orElse(null) == null) {
            return ResponseEntity.badRequest().body(new GenericResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "Error al autenticar al usuario"
            ));
        }

        return ResponseEntity.ok(
                new GenericResponse(
                        HttpStatus.OK.value(),
                        "Se verificó el usuario y se envió el código al correo"
                )
        );
    }

    @PostMapping("/change-password")
    public ResponseEntity<GenericResponse> changePassword(@RequestBody @Valid ChangePasswordReqDto changePasswordReqDto) {
        if (this.authService.changePassword(changePasswordReqDto) == null) {
            return ResponseEntity.badRequest().body(new GenericResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "Error al autenticar al usuario"
            ));
        }

        return ResponseEntity.ok(
                new GenericResponse(
                        HttpStatus.OK.value(),
                        "La contraseña se cambió correctamente"
                )
        );
    }
}

package com.congreso.domain.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserCreationDto {
    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, message = "El numero de telefono tiene que tener como minimo 8 digitos")
    private String phoneNumber;

    @NotBlank
    private String password;

    @Size(min = 13, message = "El DPI debe tener al menos 13 digitos")
    private String dpi;

    private String fotoUrl;

    private Boolean use2fa;


    Long roleId;

    public UserCreationDto() {
    }

    public UserCreationDto(String firstname, String lastname, String email, String phoneNumber, String password, String dpi) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.dpi = dpi;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getDpi() {
        return dpi;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public Boolean getUse2fa() {
        return use2fa;
    }

    public void setUse2fa(Boolean use2fa) {
        this.use2fa = use2fa;
    }
}

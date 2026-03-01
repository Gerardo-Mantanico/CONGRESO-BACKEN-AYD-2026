package com.congreso.domain.dto.user;

public class UserUpdateDto {

    String firstname;
    String lastname;
    String phoneNumber;
    String password;
    Boolean use2fa;
    Boolean isActive;
    Long roleId;
    String fotoUrl;

    public UserUpdateDto(String firstname, String lastname, String phoneNumber, String password, Boolean use2fa, Boolean isActive, Long roleId) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.use2fa = use2fa;
        this.isActive = isActive;
        this.roleId = roleId;
    }
    public UserUpdateDto() {
    }

    private boolean notNullOrEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public boolean firstnameNotEmpty() {
        return notNullOrEmpty(firstname);
    }

    public boolean lastnameNotEmpty() {
        return notNullOrEmpty(lastname);
    }

    public boolean phoneNumberNotEmpty() {
        return notNullOrEmpty(phoneNumber);
    }

    public boolean passwordNotEmpty() {
        return notNullOrEmpty(password);
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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

    public Boolean getUse2fa() {
        return use2fa;
    }

    public void setUse2fa(Boolean use2fa) {
        this.use2fa = use2fa;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}

package com.congreso.persistence.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.micrometer.common.KeyValues;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "\"user\"")
@Data
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "firstname", nullable = false, length = 100)
    private String firstname;

    @Size(max = 100)
    @NotNull
    @Column(name = "lastname", nullable = false, length = 100)
    private String lastname;

    @Size(max = 100)
    @NotNull
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Size(max = 20)
    @NotNull
    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Size(max = 100)
    @NotNull
    @Column(name = "dpi", nullable = false, length = 100)
    private String dpi;

    @Size(max = 100)
    @NotNull
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Size(max = 500)
    @Column(name = "foto_url", nullable = true, length = 500)
    private String fotoUrl;

    @ColumnDefault("true")
    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @ColumnDefault("false")
    @Column(name = "use_2fa", nullable = false)
    private Boolean use2fa = false;

    @CreationTimestamp()
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @UpdateTimestamp()
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("userRoles")
    private Set<UserRoleEntity> userRoles = new LinkedHashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles.stream()
                .map(UserRoleEntity::getRole)
                .map(RoleEntity::getName)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public Boolean getUse2fa() {
        return use2fa;
    }

    public void setUse2fa(Boolean use2fa) {
        this.use2fa = use2fa;
    }

    public RoleEntity getRole() {
        return userRoles.stream()
                .map(UserRoleEntity::getRole).findFirst().orElse(null);
    }

    public Set<UserRoleEntity> getUserRoles() {
        return userRoles;
    }

    public void setUserRole(UserRoleEntity userRole) {
        if (userRole != null) {
            userRoles.clear();
            this.userRoles.add(userRole);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

}
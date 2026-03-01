package com.congreso.persistence.entity;

import com.congreso.domain.enums.SecurityCodeStatus;
import com.congreso.domain.enums.SecurityCodeType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.time.Instant;

@Entity
@Table(name = "user_security")
public class UserSecurityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "code", nullable = false, length = 100)
    private String code;

    @NotNull
    @Column(name = "code_expiration", nullable = false)
    private Instant codeExpiration;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "code_type", columnDefinition = "security_code_type NOT NULL", nullable = false)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private SecurityCodeType codeType;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "status", columnDefinition = "security_code_status NOT NULL", nullable = false)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private SecurityCodeStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Instant getCodeExpiration() {
        return codeExpiration;
    }

    public void setCodeExpiration(Instant codeExpiration) {
        this.codeExpiration = codeExpiration;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public SecurityCodeType getCodeType() {
        return codeType;
    }

    public void setCodeType(SecurityCodeType codeType) {
        this.codeType = codeType;
    }

    public SecurityCodeStatus getStatus() {
        return status;
    }

    public void setStatus(SecurityCodeStatus status) {
        this.status = status;
    }
}
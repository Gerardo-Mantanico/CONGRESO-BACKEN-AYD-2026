package com.congreso.domain.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistsValueValidator.class)
public @interface ExistsValue {
    String message() default "El valor no existe en la base de datos";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    // En vez de entityClass, recibe el repositorio
    Class<? extends JpaRepository<?, Long>> repository();
    String fieldName() default "id";
}

package com.congreso.domain.utils;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueValueValidator.class)
public @interface UniqueValue {
    String message() default "Valor ya existe";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    // Necesitamos pasar la entidad y el campo a validar
    String fieldName();
    Class<?> entityClass();
}
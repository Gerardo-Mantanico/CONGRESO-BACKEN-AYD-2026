package com.congreso.domain.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
@Component
public class ExistsValueValidator implements ConstraintValidator<ExistsValue, Long> {

    private JpaRepository<?, Long> repository;
    private String fieldName;
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void initialize(ExistsValue constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
        Class<? extends JpaRepository<?, Long>> repositoryClass = constraintAnnotation.repository();
        this.repository = applicationContext.getBean(repositoryClass);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) return true;
        boolean exists = repository.existsById(value);
        return exists;
    }
}


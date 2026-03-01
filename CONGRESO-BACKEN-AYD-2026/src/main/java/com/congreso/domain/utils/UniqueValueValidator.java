package com.congreso.domain.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

@Component
public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {

    @PersistenceContext
    private EntityManager entityManager; // Usamos EntityManager para consultas dinámicas

    private String fieldName;
    private Class<?> entityClass;

    @Override
    public void initialize(UniqueValue constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
        this.entityClass = constraintAnnotation.entityClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) return true; // No validamos null aquí

        // JPQL dinámico: contamos si existe algún registro con ese valor en la entidad
        String jpql = "SELECT COUNT(e) FROM " + entityClass.getSimpleName() +
                " e WHERE e." + fieldName + " = :value";

        Long count = entityManager.createQuery(jpql, Long.class)
                .setParameter("value", value)
                .getSingleResult();

        return count == 0; // true si no existe, false si ya existe
    }
}

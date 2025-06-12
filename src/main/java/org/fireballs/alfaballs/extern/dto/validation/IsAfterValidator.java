package org.fireballs.alfaballs.extern.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class IsAfterValidator implements ConstraintValidator<IsAfter, Object> {

    private String afterFieldName;
    private String beforeFieldName;

    @Override
    public void initialize(IsAfter constraintAnnotation) {
        this.afterFieldName = constraintAnnotation.afterField();
        this.beforeFieldName = constraintAnnotation.beforeField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            var clazz = value.getClass();
            var afterField = clazz.getDeclaredField(afterFieldName);
            var beforeField = clazz.getDeclaredField(beforeFieldName);

            afterField.setAccessible(true);
            beforeField.setAccessible(true);

            Object afterValue = afterField.get(value);
            Object beforeValue = beforeField.get(value);

            if (afterValue == null || beforeValue == null) {
                return true;
            }

            if (afterValue instanceof LocalDateTime && beforeValue instanceof LocalDateTime) {
                return ((LocalDateTime) beforeValue).isAfter((LocalDateTime) afterValue);
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }
}

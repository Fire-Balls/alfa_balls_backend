package org.fireballs.alfaballs.extern.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsAfterValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsAfter {
    String message() default "Deadline must be after creation date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String afterField();   // createdAt
    String beforeField();  // deadline
}

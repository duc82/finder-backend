package com.duc82.finderapi.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumValidatorConstraint.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EnumValidator {
    Class<? extends Enum<?>> enumClass();
    String message() default "Invalid {enumClass}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
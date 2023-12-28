package com.example.CustomValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NumberValidation.class)

public @interface Numeric {
    String message() default "Entered value is not numeric!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

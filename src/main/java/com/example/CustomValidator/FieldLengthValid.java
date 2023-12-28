package com.example.CustomValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = FieldLengthValidator.class)
public @interface FieldLengthValid {
    String message() default "Field length must be greater than 4 and less than 10!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

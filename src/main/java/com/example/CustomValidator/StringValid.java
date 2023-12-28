package com.example.CustomValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = StringValidate.class)

public @interface StringValid {
    String message() default "Entered value is not a String!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

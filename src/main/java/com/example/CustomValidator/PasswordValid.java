package com.example.CustomValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordValidator.class)

public @interface PasswordValid {

    String message() default "Password must contain minimum 8 and maximum 10 characters, at least one uppercase letter, one lowercase letter, one number and one special character";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

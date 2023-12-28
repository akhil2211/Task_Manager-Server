package com.example.CustomValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StringValidate implements ConstraintValidator<StringValid,String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        String values=value.trim();
        if (values.isBlank()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Field cannot be null").addConstraintViolation();
            return false;
        }

        return value.matches("^[a-zA-Z]*$");
    }
}

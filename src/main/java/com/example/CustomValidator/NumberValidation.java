package com.example.CustomValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NumberValidation implements ConstraintValidator<Numeric,String> {

    @Override
    public boolean isValid(String fieldValue, ConstraintValidatorContext constraintValidatorContext) {
        String value=fieldValue.trim();
        if (value.isBlank()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Field cannot be null").addConstraintViolation();
            return false;
        }
        try {
            Double.parseDouble(fieldValue);
            return true;}
        catch (NumberFormatException e) {
            return false; }

    }

}

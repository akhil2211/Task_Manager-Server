package com.example.CustomValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidation implements ConstraintValidator<EmailValid,String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        String mail=email.toLowerCase();

        String emailid=mail.trim();
        if (emailid.isBlank()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Field cannot be null").addConstraintViolation();
            return false;
        }

        return mail.matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}");
    }
}

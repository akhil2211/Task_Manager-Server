package com.example.CustomValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FieldLengthValidator implements ConstraintValidator<FieldLengthValid, String> {

    private Logger logger = LoggerFactory.getLogger(FieldLengthValidator.class);

    private static final int MIN_LENGTH = 4;
    private static final int MAX_LENGTH = 10;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        logger.info("Message from isValid: {}", value);
          String values=value.trim();
        if (values.isBlank()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Field cannot be null").addConstraintViolation();
            return false;
        }

        int length = values.length();
        return length >= MIN_LENGTH && length <= MAX_LENGTH;
    }
}

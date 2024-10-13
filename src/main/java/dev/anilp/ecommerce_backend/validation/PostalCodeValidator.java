package dev.anilp.ecommerce_backend.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PostalCodeValidator implements ConstraintValidator<PostalCode, String> {

    @Override
    public boolean isValid(String postalCode, ConstraintValidatorContext constraintValidatorContext) {
        if (postalCode == null) {
            return false;
        }
        return postalCode.matches("^(0[1-9]|[1-7][0-9]|8[01])(00[1-9]|0[1-9][0-9]|[1-9][0-9][0-9])$");
    }
}
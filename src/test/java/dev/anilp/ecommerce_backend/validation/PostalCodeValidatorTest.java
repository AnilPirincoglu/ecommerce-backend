package dev.anilp.ecommerce_backend.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class PostalCodeValidatorTest {

    @Mock
    private ConstraintValidatorContext context;

    @InjectMocks
    private PostalCodeValidator postalCodeValidator;

    @ParameterizedTest
    @ValueSource(strings = {
            "34105", "01455", "55178", "35565"
    })
    void itShouldBeTrueWhenPostalCodeIsValid(String postalCode) {
        assertTrue(postalCodeValidator.isValid(postalCode, context));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "90156", "1515", "82", "345656"
    })
    void itShouldBeFalseWhenPostalCodeIsInvalid(String postalCode) {
        assertFalse(postalCodeValidator.isValid(postalCode, context));
    }

    @Test
    void itShouldBeFalseWhenPostalCodeIsNull() {
        assertFalse(postalCodeValidator.isValid(null, context));
    }

    @Test
    void itShouldBeFalseWhenPostalCodeIsEmpty() {
        assertFalse(postalCodeValidator.isValid("", context));
    }
}
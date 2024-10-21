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
class PhoneNumberValidatorTest {

    @Mock
    private ConstraintValidatorContext context;
    @InjectMocks
    private PhoneNumberValidator phoneNumberValidator;

    @ParameterizedTest
    @ValueSource(strings = {
            "2123456789", "5301234567", "3123456789", "5591234567"
    })
    void itShouldBeTrueWhenPhoneNumberIsValid(String phoneNumber) {
        assertTrue(phoneNumberValidator.isValid(phoneNumber, context));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1234567890", "555123456", "55512345678", "5551234567a"
    })
    void itShouldBeFalseWhenPhoneNumberIsInvalid(String phoneNumber) {
        assertFalse(phoneNumberValidator.isValid(phoneNumber, context));
    }

    @Test
    void itShouldBeFalseWhenPhoneNumberIsNull() {
        assertFalse(phoneNumberValidator.isValid(null, context));
    }

    @Test
    void itShouldBeFalseWhenPhoneNumberIsEmpty() {
        assertFalse(phoneNumberValidator.isValid("", context));
    }
}
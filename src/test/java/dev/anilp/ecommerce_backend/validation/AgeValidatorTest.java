package dev.anilp.ecommerce_backend.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class AgeValidatorTest {

    @Mock
    private ConstraintValidatorContext context;

    @InjectMocks
    private AgeValidator ageValidator;

    @ParameterizedTest
    @MethodSource("validAges")
    void itShouldBeTrueWhenAgeIsValid(LocalDate dateOfBirth) {
        assertTrue(ageValidator.isValid(dateOfBirth, context));
    }

    @ParameterizedTest
    @MethodSource("invalidAges")
    void itShouldBeFalseWhenAgeIsInvalid(LocalDate dateOfBirth) {
        assertFalse(ageValidator.isValid(dateOfBirth, context));
    }

    @Test
    void itShouldBeFalseWhenAgeIsNull() {
        assertFalse(ageValidator.isValid(null, context));
    }

    private static Stream<LocalDate> validAges() {
        return Stream.of(
                LocalDate.now().minusYears(18),
                LocalDate.now().minusYears(25),
                LocalDate.now().minusYears(50)
        );
    }

    private static Stream<LocalDate> invalidAges() {
        return Stream.of(
                LocalDate.now().minusYears(17).plusDays(1),
                LocalDate.now().minusYears(10),
                LocalDate.now()
        );
    }
}
package dev.anilp.ecommerce_backend.dto.user;

import dev.anilp.ecommerce_backend.entity.user.Gender;
import dev.anilp.ecommerce_backend.validation.Age;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateUserRequestDTO(
        @NotBlank(message = "First name cannot be blank")
        @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters")
        String firstName,

        @NotBlank(message = "Last name cannot be blank")
        @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters")
        String lastName,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email should be valid")
        String email,

        @NotNull(message = "Gender cannot be null")
        Gender gender,

        @NotNull(message = "Date of birth cannot be null. Please use the format yyyy-MM-dd.")
        @Past(message = "Date of birth must be a past date")
        @Age
        LocalDate dateOfBirth
) {
}

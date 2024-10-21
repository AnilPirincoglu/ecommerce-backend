package dev.anilp.ecommerce_backend.dto.phone;

import dev.anilp.ecommerce_backend.entity.phone.PhoneType;
import dev.anilp.ecommerce_backend.validation.PhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePhoneRequestDTO(
        @NotNull(message = "Phone type cannot be null")
        PhoneType phoneType,

        @NotBlank(message = "Phone number cannot be blank.")
        @PhoneNumber
        String phoneNumber
) {
}

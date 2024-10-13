package dev.anilp.ecommerce_backend.dto.phone_number;

import dev.anilp.ecommerce_backend.entity.phone_number.PhoneType;
import dev.anilp.ecommerce_backend.validation.EnumValue;
import dev.anilp.ecommerce_backend.validation.Phone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePhoneNumberRequestDTO(
        @NotNull(message = "Phone type cannot be null")
        @EnumValue(enumClass = PhoneType.class, message = "Phone type can be HOME, WORK or MOBILE")
        PhoneType phoneType,

        @NotBlank(message = "Phone number cannot be blank.")
        @Phone
        String phoneNumber
) {
}

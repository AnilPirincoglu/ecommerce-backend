package dev.anilp.ecommerce_backend.dto.phone_number;

import dev.anilp.ecommerce_backend.entity.phone_number.PhoneType;

public record PhoneNumberResponseDTO(
        PhoneType phoneType,
        String phoneNumber
) {
}

package dev.anilp.ecommerce_backend.dto.phone;

import dev.anilp.ecommerce_backend.entity.phone.PhoneType;

public record PhoneResponseDTO(
        Long id,
        PhoneType phoneType,
        String phoneNumber
) {
}

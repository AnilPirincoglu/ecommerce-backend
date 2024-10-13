package dev.anilp.ecommerce_backend.dto.address;

import dev.anilp.ecommerce_backend.entity.address.AddressType;

public record AddressResponseDTO(
        AddressType addressType,
        String city,
        String postalCode,
        String district,
        String street,
        String addressLine
) {
}

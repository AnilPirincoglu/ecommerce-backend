package dev.anilp.ecommerce_backend.dto.address;

import dev.anilp.ecommerce_backend.entity.address.AddressType;
import dev.anilp.ecommerce_backend.validation.PostalCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateAddressRequestDTO(
        @NotNull(message = "Address type cannot be null")
        AddressType addressType,

        @NotBlank(message = "Address line cannot be blank")
        @Size(min = 10, max = 255, message = "Address line must be between 10 and 255 characters")
        String addressLine,

        @NotBlank(message = "Street cannot be blank")
        @Size(min = 5, max = 50, message = "Street must be between 5 and 50 characters")
        String street,

        @NotBlank(message = "District cannot be blank")
        @Size(min = 5, max = 50, message = "District must be between 5 and 50 characters")
        String district,

        @NotBlank(message = "City cannot be blank")
        @Size(min = 5, max = 50, message = "City must be between 5 and 50 characters")
        String city,

        @NotBlank(message = "Postal code cannot be blank")
        @PostalCode
        String postalCode
) {
}

package dev.anilp.ecommerce_backend.dto.user;

import dev.anilp.ecommerce_backend.dto.address.AddressResponseDTO;
import dev.anilp.ecommerce_backend.dto.phone_number.PhoneNumberResponseDTO;
import dev.anilp.ecommerce_backend.entity.user.Gender;

import java.time.LocalDate;
import java.util.List;

public record UserResponseDTO(Long id,
                              String firstName,
                              String lastName,
                              String email,
                              Gender gender,
                              LocalDate dateOfBirth,
                              List<AddressResponseDTO> addresses,
                              List<PhoneNumberResponseDTO> phoneNumbers) {
}

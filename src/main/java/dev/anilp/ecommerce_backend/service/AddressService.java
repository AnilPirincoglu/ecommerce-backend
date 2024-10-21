package dev.anilp.ecommerce_backend.service;

import dev.anilp.ecommerce_backend.dto.address.AddressResponseDTO;
import dev.anilp.ecommerce_backend.dto.address.CreateAddressRequestDTO;
import dev.anilp.ecommerce_backend.dto.address.UpdateAddressRequestDTO;

import java.util.List;

public interface AddressService {

    AddressResponseDTO getAddressOfUserById(Long addressId, Long userId);

    List<AddressResponseDTO> getAllAddressesOfUser(Long userId);

    void addAddressToUser(Long userId, CreateAddressRequestDTO createAddress);

    void updateAddressOfUser(Long addressId, Long userId, UpdateAddressRequestDTO updateAddress);

    void deleteAddressFromUser(Long addressId, Long userId);
}

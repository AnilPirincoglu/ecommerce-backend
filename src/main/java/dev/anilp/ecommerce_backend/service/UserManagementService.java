package dev.anilp.ecommerce_backend.service;

import dev.anilp.ecommerce_backend.dto.user.UserResponseDTO;

import java.util.List;

public interface UserManagementService {
    UserResponseDTO getUserById(Long id);

    UserResponseDTO getUserByEmail(String email);

    UserResponseDTO getUserByPhoneNumber(String phoneNumber);

    List<UserResponseDTO> getAllUsers();
}

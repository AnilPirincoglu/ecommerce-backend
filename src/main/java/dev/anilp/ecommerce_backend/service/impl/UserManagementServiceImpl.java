package dev.anilp.ecommerce_backend.service.impl;

import dev.anilp.ecommerce_backend.dto.user.UserResponseDTO;
import dev.anilp.ecommerce_backend.exception.exception_class.ResourceNotFoundException;
import dev.anilp.ecommerce_backend.mapper.UserMapper;
import dev.anilp.ecommerce_backend.repository.UserRepository;
import dev.anilp.ecommerce_backend.service.UserManagementService;
import org.springframework.stereotype.Service;

import java.util.List;

import static dev.anilp.ecommerce_backend.exception.ExceptionConstant.EMAIL;
import static dev.anilp.ecommerce_backend.exception.ExceptionConstant.ID;
import static dev.anilp.ecommerce_backend.exception.ExceptionConstant.PHONE_NUMBER;
import static dev.anilp.ecommerce_backend.exception.ExceptionConstant.USER;

@Service
public class UserManagementServiceImpl implements UserManagementService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    public UserManagementServiceImpl(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public UserResponseDTO getUserById(Long userId) {
        return mapper.userToResponse(
                userRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException(USER, ID, userId))
        );
    }

    public UserResponseDTO getUserByEmail(String email) {
        return mapper.userToResponse(
                userRepository.findByEmail(email)
                        .orElseThrow(() -> new ResourceNotFoundException(USER, EMAIL, email))
        );
    }

    public UserResponseDTO getUserByPhoneNumber(String phoneNumber) {
        return mapper.userToResponse(
                userRepository.findByPhoneNumber(phoneNumber)
                        .orElseThrow(() -> new ResourceNotFoundException(USER, PHONE_NUMBER, phoneNumber))
        );
    }

    public List<UserResponseDTO> getAllUsers() {
        return mapper.usersToResponsesList(
                userRepository.findAll()
        );
    }
}

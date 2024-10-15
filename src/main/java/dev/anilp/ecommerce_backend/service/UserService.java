package dev.anilp.ecommerce_backend.service;

import dev.anilp.ecommerce_backend.dto.user.CreateUserRequestDTO;
import dev.anilp.ecommerce_backend.dto.user.UpdateUserRequestDTO;
import dev.anilp.ecommerce_backend.dto.user.UserResponseDTO;
import dev.anilp.ecommerce_backend.entity.user.User;
import dev.anilp.ecommerce_backend.exception.exception_class.DuplicateResourceException;
import dev.anilp.ecommerce_backend.exception.exception_class.ResourceNotFoundException;
import dev.anilp.ecommerce_backend.mapper.UserMapper;
import dev.anilp.ecommerce_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static dev.anilp.ecommerce_backend.exception.ExceptionConstant.EMAIL;
import static dev.anilp.ecommerce_backend.exception.ExceptionConstant.ID;
import static dev.anilp.ecommerce_backend.exception.ExceptionConstant.USER;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    public UserService(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public UserResponseDTO getUserById(Long userId) {
        return mapper.userToResponse(
                findUserById(userId)
        );
    }

    public UserResponseDTO getUserByEmail(String email) {
        return mapper.userToResponse(
                userRepository.findByEmail(email)
                        .orElseThrow(() -> new ResourceNotFoundException(USER, ID, email))
        );
    }

    public List<UserResponseDTO> getAllUsers() {
        return mapper.usersToResponsesList(
                userRepository.findAll()
        );
    }

    public void createUser(CreateUserRequestDTO createUser) {
        checkEmailExistence(createUser.email());
        userRepository.save(
                mapper.requestToUser(createUser)
        );
    }

    public void updateUser(Long userId, UpdateUserRequestDTO updateUser) {
        checkEmailExistence(updateUser.email());
        User user = findUserById(userId);
        mapper.updateUserFromDto(user, updateUser);
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.delete(
                findUserById(userId)
        );
    }

    private void checkEmailExistence(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateResourceException(USER, EMAIL, email);
        }
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, id.toString()));
    }
}

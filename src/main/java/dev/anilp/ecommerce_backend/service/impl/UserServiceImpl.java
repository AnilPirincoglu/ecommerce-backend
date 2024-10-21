package dev.anilp.ecommerce_backend.service.impl;

import dev.anilp.ecommerce_backend.dto.user.CreateUserRequestDTO;
import dev.anilp.ecommerce_backend.dto.user.UpdateUserRequestDTO;
import dev.anilp.ecommerce_backend.entity.user.User;
import dev.anilp.ecommerce_backend.exception.exception_class.DuplicateResourceException;
import dev.anilp.ecommerce_backend.exception.exception_class.ResourceNotFoundException;
import dev.anilp.ecommerce_backend.mapper.UserMapper;
import dev.anilp.ecommerce_backend.repository.UserRepository;
import dev.anilp.ecommerce_backend.service.UserService;
import org.springframework.stereotype.Service;

import static dev.anilp.ecommerce_backend.exception.ExceptionConstant.EMAIL;
import static dev.anilp.ecommerce_backend.exception.ExceptionConstant.ID;
import static dev.anilp.ecommerce_backend.exception.ExceptionConstant.USER;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, id));
    }

    public void saveUser(CreateUserRequestDTO createUser) {
        checkEmailExistence(createUser.email());
        userRepository.save(
                mapper.requestToUser(createUser)
        );
    }

    public void updateUserById(Long userId, UpdateUserRequestDTO updateUser) {
        User user = findUserById(userId);
        if (!user.getEmail().equals(updateUser.email())) {
            checkEmailExistence(updateUser.email());
        }
        mapper.updateUserFromDto(user, updateUser);
        userRepository.save(user);
    }

    public void deleteUserById(Long userId) {
        userRepository.delete(
                findUserById(userId)
        );
    }

    private void checkEmailExistence(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateResourceException(USER, EMAIL, email);
        }
    }
}

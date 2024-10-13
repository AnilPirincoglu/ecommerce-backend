package dev.anilp.ecommerce_backend.servise;

import dev.anilp.ecommerce_backend.dto.user.UserResponseDTO;
import dev.anilp.ecommerce_backend.entity.user.User;
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
        return mapper.UsersToResponsesList(
                userRepository.findAll()
        );
    }
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, id.toString()));
    }
}

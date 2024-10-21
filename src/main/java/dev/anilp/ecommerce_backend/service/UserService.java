package dev.anilp.ecommerce_backend.service;

import dev.anilp.ecommerce_backend.dto.user.CreateUserRequestDTO;
import dev.anilp.ecommerce_backend.dto.user.UpdateUserRequestDTO;
import dev.anilp.ecommerce_backend.entity.user.User;

public interface UserService {
    User findUserById(Long userId);

    void saveUser(CreateUserRequestDTO createUser);

    void updateUserById(Long userId, UpdateUserRequestDTO updateUser);

    void deleteUserById(Long userId);
}

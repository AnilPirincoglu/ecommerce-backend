package dev.anilp.ecommerce_backend.controller;

import dev.anilp.ecommerce_backend.dto.user.CreateUserRequestDTO;
import dev.anilp.ecommerce_backend.dto.user.UpdateUserRequestDTO;
import dev.anilp.ecommerce_backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void createUser(@Valid @RequestBody CreateUserRequestDTO createUser) {
        userService.saveUser(createUser);
    }

    @PutMapping("{userId}")
    public void updateUser(@PathVariable Long userId,
                           @Valid @RequestBody UpdateUserRequestDTO updateUser) {
        userService.updateUserById(userId, updateUser);
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUserById(userId);
    }

}

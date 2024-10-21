package dev.anilp.ecommerce_backend.controller;

import dev.anilp.ecommerce_backend.dto.user.UserResponseDTO;
import dev.anilp.ecommerce_backend.service.UserManagementService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/management/users")
public class UserManagementController {
    private final UserManagementService userManagementService;

    public UserManagementController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @GetMapping("{userId}")
    public UserResponseDTO getUserById(@PathVariable Long userId) {
        return userManagementService.getUserById(userId);
    }

    @GetMapping("emails/{email}")
    public UserResponseDTO getUserByEmail(@PathVariable String email) {
        return userManagementService.getUserByEmail(email);
    }

    @GetMapping("phones/{phoneNumber}")
    public UserResponseDTO getUserByPhoneNumber(@PathVariable String phoneNumber) {
        return userManagementService.getUserByPhoneNumber(phoneNumber);
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userManagementService.getAllUsers();
    }
}

package dev.anilp.ecommerce_backend.controller;

import dev.anilp.ecommerce_backend.dto.phone.CreatePhoneRequestDTO;
import dev.anilp.ecommerce_backend.dto.phone.PhoneResponseDTO;
import dev.anilp.ecommerce_backend.dto.phone.UpdatePhoneRequestDTO;
import dev.anilp.ecommerce_backend.service.PhoneService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/phones")
public class PhoneController {
    private final PhoneService phoneService;

    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @GetMapping("{phoneId}/users/{userId}")
    public PhoneResponseDTO getPhoneOfUserById(@PathVariable Long phoneId,
                                               @PathVariable Long userId) {
        return phoneService.getPhoneOfUser(phoneId, userId);
    }

    @GetMapping("users/{userId}")
    public List<PhoneResponseDTO> getPhonesOfUser(@PathVariable Long userId) {
        return phoneService.getAllPhonesFromUser(userId);
    }

    @PostMapping("users/{userId}")
    public void addPhoneToUser(@PathVariable Long userId,
                               @Valid @RequestBody CreatePhoneRequestDTO createPhone) {
        phoneService.addPhoneToUser(userId, createPhone);
    }

    @PutMapping("{phoneId}/users/{userId}")
    public void updatePhoneOfUser(@PathVariable Long phoneId,
                                  @PathVariable Long userId,
                                  @Valid @RequestBody UpdatePhoneRequestDTO updatePhone) {
        phoneService.updatePhoneOfUser(phoneId, userId, updatePhone);
    }

    @DeleteMapping("{phoneId}/users/{userId}")
    public void deletePhoneFromUser(@PathVariable Long phoneId,
                                    @PathVariable Long userId) {
        phoneService.deletePhoneFromUser(phoneId, userId);
    }
}

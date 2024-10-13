package dev.anilp.ecommerce_backend.controller;

import dev.anilp.ecommerce_backend.dto.phone_number.CreatePhoneNumberRequestDTO;
import dev.anilp.ecommerce_backend.dto.phone_number.PhoneNumberResponseDTO;
import dev.anilp.ecommerce_backend.dto.phone_number.UpdatePhoneNumberRequestDTO;
import dev.anilp.ecommerce_backend.servise.PhoneNumberService;
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
@RequestMapping("api/v1/phone-numbers")
public class PhoneNumberController {
    private final PhoneNumberService phoneNumberService;

    public PhoneNumberController(PhoneNumberService phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
    }

    @GetMapping("{phoneNumberId}/users/{userId}")
    public PhoneNumberResponseDTO getPhoneNumberOfUserById(@PathVariable Long userId,
                                                           @PathVariable Long phoneNumberId) {
        return phoneNumberService.getPhoneNumberOfUserById(userId, phoneNumberId);
    }

    @GetMapping("users/{userId}")
    public List<PhoneNumberResponseDTO> getPhoneNumbersOfUser(@PathVariable Long userId) {
        return phoneNumberService.getPhoneNumbersOfUser(userId);
    }

    @PostMapping("users/{userId}")
    public void addPhoneNumberToUser(@PathVariable Long userId,
                                     @Valid @RequestBody CreatePhoneNumberRequestDTO createPhoneNumber) {
        phoneNumberService.addPhoneNumberToUser(userId, createPhoneNumber);
    }

    @PutMapping("{phoneNumberId}/users/{userId}")
    public void updatePhoneNumberOfUser(@PathVariable Long userId,
                                        @PathVariable Long phoneNumberId,
                                        @Valid @RequestBody UpdatePhoneNumberRequestDTO updatePhoneNumber) {
        phoneNumberService.updatePhoneNumberOfUser(userId, phoneNumberId, updatePhoneNumber);
    }

    @DeleteMapping("{phoneNumberId}/users/{userId}")
    public void deletePhoneNumberFromUser(@PathVariable Long userId,
                                          @PathVariable Long phoneNumberId) {
        phoneNumberService.deletePhoneNumberFromUser(userId, phoneNumberId);
    }
}

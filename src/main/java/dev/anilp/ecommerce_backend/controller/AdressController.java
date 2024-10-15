package dev.anilp.ecommerce_backend.controller;

import dev.anilp.ecommerce_backend.dto.address.AddressResponseDTO;
import dev.anilp.ecommerce_backend.dto.address.CreateAddressRequestDTO;
import dev.anilp.ecommerce_backend.dto.address.UpdateAddressRequestDTO;
import dev.anilp.ecommerce_backend.service.AddressService;
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
@RequestMapping("api/v1/addresses")
public class AdressController {

    private final AddressService addressService;

    public AdressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("{addressId}/users/{userId}")
    public AddressResponseDTO getAddressOfUserById(@PathVariable Long userId,
                                                   @PathVariable Long addressId) {
        return addressService.getAddressOfUserById(userId, addressId);
    }

    @GetMapping("users/{userId}")
    public List<AddressResponseDTO> getAddressesOfUser(@PathVariable Long userId) {
        return addressService.getAddressesOfUser(userId);
    }

    @PostMapping("users/{userId}")
    public void addAddressToUser(@PathVariable Long userId,
                                 @Valid @RequestBody CreateAddressRequestDTO createAddress) {
        addressService.addAddressToUser(userId, createAddress);
    }

    @PutMapping("{addressId}/users/{userId}")
    public void updateAddressOfUser(@PathVariable Long userId,
                                    @PathVariable Long addressId,
                                    @Valid @RequestBody UpdateAddressRequestDTO updateAddress) {
        addressService.updateAddressOfUser(userId, addressId, updateAddress);
    }

    @DeleteMapping("{addressId}/users/{userId}")
    public void deleteAddressFromUser(@PathVariable Long userId,
                                      @PathVariable Long addressId) {
        addressService.deleteAddressFromUser(userId, addressId);
    }
}

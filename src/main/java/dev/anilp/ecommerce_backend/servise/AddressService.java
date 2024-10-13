package dev.anilp.ecommerce_backend.servise;

import dev.anilp.ecommerce_backend.dto.address.AddressResponseDTO;
import dev.anilp.ecommerce_backend.entity.address.Address;
import dev.anilp.ecommerce_backend.entity.user.User;
import dev.anilp.ecommerce_backend.exception.exception_class.ResourceNotFoundException;
import dev.anilp.ecommerce_backend.mapper.UserMapper;
import dev.anilp.ecommerce_backend.repository.AddressRepository;
import org.springframework.stereotype.Service;

import static dev.anilp.ecommerce_backend.exception.ExceptionConstant.ADDRESS;
import static dev.anilp.ecommerce_backend.exception.ExceptionConstant.ID;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserService userService;
    private final UserMapper mapper;

    public AddressService(AddressRepository addressRepository, UserService userService, UserMapper mapper) {
        this.addressRepository = addressRepository;
        this.userService = userService;
        this.mapper = mapper;
    }

    public AddressResponseDTO getAddressOfUserById(Long userId, Long addressId) {
        User user = userService.findUserById(userId);

        return mapper.addressToResponse(
                findUserAddressById(addressId, user)
        );
    }

    private Address findUserAddressById(Long addressId, User user) {
        return user.getAddresses()
                .stream()
                .filter(address -> address.getId().equals(addressId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(ADDRESS, ID, addressId.toString()));
    }

}

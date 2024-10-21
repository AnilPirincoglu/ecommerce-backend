package dev.anilp.ecommerce_backend.service.impl;

import dev.anilp.ecommerce_backend.dto.address.AddressResponseDTO;
import dev.anilp.ecommerce_backend.dto.address.CreateAddressRequestDTO;
import dev.anilp.ecommerce_backend.dto.address.UpdateAddressRequestDTO;
import dev.anilp.ecommerce_backend.entity.address.Address;
import dev.anilp.ecommerce_backend.entity.user.User;
import dev.anilp.ecommerce_backend.exception.exception_class.ResourceNotFoundException;
import dev.anilp.ecommerce_backend.mapper.AddressMapper;
import dev.anilp.ecommerce_backend.repository.AddressRepository;
import dev.anilp.ecommerce_backend.service.AddressService;
import dev.anilp.ecommerce_backend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

import static dev.anilp.ecommerce_backend.exception.ExceptionConstant.ADDRESS;
import static dev.anilp.ecommerce_backend.exception.ExceptionConstant.ADDRESS_AND_USER_ID;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final UserService userService;
    private final AddressMapper mapper;

    public AddressServiceImpl(AddressRepository addressRepository, UserService userService, AddressMapper mapper) {
        this.addressRepository = addressRepository;
        this.userService = userService;
        this.mapper = mapper;
    }

    public AddressResponseDTO getAddressOfUserById(Long addressId, Long userId) {
        return mapper.addressToResponse(
                findAddressByIdAndUserId(addressId, userId)
        );
    }

    public List<AddressResponseDTO> getAllAddressesOfUser(Long userId) {
        return mapper.addressesToResponsesList(
                addressRepository.findAllAddressesByUserId(userId)
        );
    }

    public void addAddressToUser(Long userId, CreateAddressRequestDTO createAddress) {
        User user = userService.findUserById(userId);
        Address createdAddress = mapper.requestToAddress(createAddress);
        user.addAddress(createdAddress);
        addressRepository.save(createdAddress);
    }

    public void updateAddressOfUser(Long addressId, Long userId, UpdateAddressRequestDTO updateAddress) {
        Address address = findAddressByIdAndUserId(addressId, userId);
        mapper.updateAddressFromDto(address, updateAddress);
        addressRepository.save(address);
    }

    public void deleteAddressFromUser(Long addressId, Long userId) {
        Address address = findAddressByIdAndUserId(addressId, userId);
        addressRepository.delete(address);
    }

    private Address findAddressByIdAndUserId(Long addressId, Long userId) {
        return addressRepository.findAddressByIdAndUserId(addressId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(ADDRESS, ADDRESS_AND_USER_ID,
                        String.format("Address id = %s, User id = %s", addressId, userId)));
    }
}

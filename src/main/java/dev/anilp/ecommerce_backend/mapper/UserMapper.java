package dev.anilp.ecommerce_backend.mapper;

import dev.anilp.ecommerce_backend.dto.address.AddressResponseDTO;
import dev.anilp.ecommerce_backend.dto.address.CreateAddressRequestDTO;
import dev.anilp.ecommerce_backend.dto.address.UpdateAddressRequestDTO;
import dev.anilp.ecommerce_backend.dto.phone_number.CreatePhoneNumberRequestDTO;
import dev.anilp.ecommerce_backend.dto.phone_number.PhoneNumberResponseDTO;
import dev.anilp.ecommerce_backend.dto.phone_number.UpdatePhoneNumberRequestDTO;
import dev.anilp.ecommerce_backend.dto.user.CreateUserRequestDTO;
import dev.anilp.ecommerce_backend.dto.user.UpdateUserRequestDTO;
import dev.anilp.ecommerce_backend.dto.user.UserResponseDTO;
import dev.anilp.ecommerce_backend.entity.address.Address;
import dev.anilp.ecommerce_backend.entity.phone_number.PhoneNumber;
import dev.anilp.ecommerce_backend.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO userToResponse(User user);

    AddressResponseDTO addressToResponse(Address address);

    PhoneNumberResponseDTO phoneNumberToResponse(PhoneNumber phoneNumber);

    List<UserResponseDTO> UsersToResponsesList(List<User> users);

    List<AddressResponseDTO> AddressesToResponsesList(List<Address> addresses);

    List<PhoneNumberResponseDTO> PhoneNumbersToResponsesList(List<PhoneNumber> phoneNumbers);

    User requestToUser(CreateUserRequestDTO createUser);

    User requestToUser(UpdateUserRequestDTO updateUser);

    Address requestToAddress(CreateAddressRequestDTO createAddress);

    Address requestToAddress(UpdateAddressRequestDTO updateAddress);

    PhoneNumber requestToPhoneNumber(CreatePhoneNumberRequestDTO createPhoneNumber);

    PhoneNumber requestToPhoneNumber(UpdatePhoneNumberRequestDTO updatePhoneNumber);

    void updateUserFromDto(@MappingTarget User user, UpdateUserRequestDTO updateRequest);

    void updatePhoneNumberFromDto(@MappingTarget PhoneNumber phoneNumber, UpdatePhoneNumberRequestDTO updateRequest);

    void updateAddressFromDto(@MappingTarget Address address, UpdateAddressRequestDTO updateRequest);

}
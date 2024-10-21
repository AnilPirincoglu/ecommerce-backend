package dev.anilp.ecommerce_backend.mapper;

import dev.anilp.ecommerce_backend.dto.address.AddressResponseDTO;
import dev.anilp.ecommerce_backend.dto.address.CreateAddressRequestDTO;
import dev.anilp.ecommerce_backend.dto.address.UpdateAddressRequestDTO;
import dev.anilp.ecommerce_backend.entity.address.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressResponseDTO addressToResponse(Address address);

    List<AddressResponseDTO> addressesToResponsesList(List<Address> addresses);

    Address requestToAddress(CreateAddressRequestDTO createAddress);

    void updateAddressFromDto(@MappingTarget Address address, UpdateAddressRequestDTO updateRequest);
}

package dev.anilp.ecommerce_backend.mapper;

import dev.anilp.ecommerce_backend.dto.phone.CreatePhoneRequestDTO;
import dev.anilp.ecommerce_backend.dto.phone.PhoneResponseDTO;
import dev.anilp.ecommerce_backend.dto.phone.UpdatePhoneRequestDTO;
import dev.anilp.ecommerce_backend.entity.phone.Phone;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PhoneMapper {
    PhoneResponseDTO phoneToResponse(Phone phone);

    List<PhoneResponseDTO> phonesToResponsesList(List<Phone> phones);

    Phone requestToPhone(CreatePhoneRequestDTO createPhone);

    void updatePhoneFromDto(@MappingTarget Phone phone, UpdatePhoneRequestDTO updateRequest);
}

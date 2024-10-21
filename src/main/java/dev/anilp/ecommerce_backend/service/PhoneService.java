package dev.anilp.ecommerce_backend.service;

import dev.anilp.ecommerce_backend.dto.phone.CreatePhoneRequestDTO;
import dev.anilp.ecommerce_backend.dto.phone.PhoneResponseDTO;
import dev.anilp.ecommerce_backend.dto.phone.UpdatePhoneRequestDTO;

import java.util.List;

public interface PhoneService {

    PhoneResponseDTO getPhoneOfUser(Long phoneId, Long userId);

    List<PhoneResponseDTO> getAllPhonesFromUser(Long userId);

    void addPhoneToUser(Long userId, CreatePhoneRequestDTO createPhone);

    void updatePhoneOfUser(Long phoneId, Long userId, UpdatePhoneRequestDTO updatePhone);

    void deletePhoneFromUser(Long phoneId, Long userId);
}

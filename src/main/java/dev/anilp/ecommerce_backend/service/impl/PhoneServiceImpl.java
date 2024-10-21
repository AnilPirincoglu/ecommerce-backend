package dev.anilp.ecommerce_backend.service.impl;

import dev.anilp.ecommerce_backend.dto.phone.CreatePhoneRequestDTO;
import dev.anilp.ecommerce_backend.dto.phone.PhoneResponseDTO;
import dev.anilp.ecommerce_backend.dto.phone.UpdatePhoneRequestDTO;
import dev.anilp.ecommerce_backend.entity.phone.Phone;
import dev.anilp.ecommerce_backend.entity.user.User;
import dev.anilp.ecommerce_backend.exception.exception_class.DuplicateResourceException;
import dev.anilp.ecommerce_backend.exception.exception_class.ResourceNotFoundException;
import dev.anilp.ecommerce_backend.mapper.PhoneMapper;
import dev.anilp.ecommerce_backend.repository.PhoneRepository;
import dev.anilp.ecommerce_backend.service.PhoneService;
import dev.anilp.ecommerce_backend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

import static dev.anilp.ecommerce_backend.exception.ExceptionConstant.PHONE;
import static dev.anilp.ecommerce_backend.exception.ExceptionConstant.PHONE_AND_USER_ID;
import static dev.anilp.ecommerce_backend.exception.ExceptionConstant.PHONE_NUMBER;

@Service
public class PhoneServiceImpl implements PhoneService {
    private final PhoneRepository phoneRepository;
    private final UserService userService;
    private final PhoneMapper mapper;

    public PhoneServiceImpl(PhoneRepository phoneRepository, UserService userService, PhoneMapper mapper) {
        this.phoneRepository = phoneRepository;
        this.userService = userService;
        this.mapper = mapper;
    }

    public PhoneResponseDTO getPhoneOfUser(Long phoneId, Long userId) {
        return mapper.phoneToResponse(
                findPhoneByPhoneIdAndUserId(phoneId, userId)
        );
    }

    public List<PhoneResponseDTO> getAllPhonesFromUser(Long userId) {
        return mapper.phonesToResponsesList(
                phoneRepository.findPhonesByUserId(userId)
        );
    }

    public void addPhoneToUser(Long userId, CreatePhoneRequestDTO createPhone) {
        User user = userService.findUserById(userId);
        checkPhoneNumberExistence(createPhone.phoneNumber());
        Phone phone = mapper.requestToPhone(createPhone);
        user.addPhone(phone);
        phoneRepository.save(phone);
    }

    public void updatePhoneOfUser(Long phoneId, Long userId, UpdatePhoneRequestDTO updatePhone) {
        Phone phone = findPhoneByPhoneIdAndUserId(userId, phoneId);
        if (!phone.getPhoneNumber().equals(updatePhone.phoneNumber())) {
            checkPhoneNumberExistence(updatePhone.phoneNumber());
        }
        mapper.updatePhoneFromDto(phone, updatePhone);
        phoneRepository.save(phone);
    }

    public void deletePhoneFromUser(Long phoneId, Long userId) {
        Phone phone = findPhoneByPhoneIdAndUserId(userId, phoneId);
        phoneRepository.delete(phone);
    }

    private void checkPhoneNumberExistence(String phoneNumber) {
        if (phoneRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DuplicateResourceException(PHONE, PHONE_NUMBER, phoneNumber);
        }
    }

    private Phone findPhoneByPhoneIdAndUserId(Long phoneId, Long userId) {
        return phoneRepository.findPhoneByIdAndUserId(phoneId, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(PHONE, PHONE_AND_USER_ID,
                                String.format("Phone id = %s, User id = %s", phoneId, userId)));
    }
}

package dev.anilp.ecommerce_backend.servise;

import dev.anilp.ecommerce_backend.dto.phone_number.PhoneNumberResponseDTO;
import dev.anilp.ecommerce_backend.entity.phone_number.PhoneNumber;
import dev.anilp.ecommerce_backend.entity.user.User;
import dev.anilp.ecommerce_backend.exception.exception_class.ResourceNotFoundException;
import dev.anilp.ecommerce_backend.mapper.UserMapper;
import dev.anilp.ecommerce_backend.repository.PhoneNumberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static dev.anilp.ecommerce_backend.exception.ExceptionConstant.ID;
import static dev.anilp.ecommerce_backend.exception.ExceptionConstant.PHONE_NUMBER;

@Service
public class PhoneNumberService {
    private final PhoneNumberRepository phoneNumberRepository;
    private final UserService userService;
    private final UserMapper mapper;

    public PhoneNumberService(PhoneNumberRepository phoneNumberRepository, UserService userService, UserMapper mapper) {
        this.phoneNumberRepository = phoneNumberRepository;
        this.userService = userService;
        this.mapper = mapper;
    }

    public PhoneNumberResponseDTO getPhoneNumberOfUserById(Long userId, Long phoneNumberId) {
        User user = userService.findUserById(userId);

        return mapper.phoneNumberToResponse(
                findUserPhoneNumberById(phoneNumberId, user)
        );
    }

    public List<PhoneNumberResponseDTO> getPhoneNumbersOfUser(Long userId) {
        return mapper.PhoneNumbersToResponsesList(
                userService.findUserById(userId).getPhoneNumbers()
        );
    }

    private PhoneNumber findUserPhoneNumberById(Long phoneNumberId, User user) {
        return user.getPhoneNumbers()
                .stream()
                .filter(phoneNumber -> phoneNumber.getId().equals(phoneNumberId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(PHONE_NUMBER, ID, phoneNumberId.toString()));
    }
}

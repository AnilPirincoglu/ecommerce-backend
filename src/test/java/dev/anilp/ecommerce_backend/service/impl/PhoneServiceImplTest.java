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
import dev.anilp.ecommerce_backend.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static dev.anilp.ecommerce_backend.entity.phone.PhoneType.HOME;
import static dev.anilp.ecommerce_backend.entity.phone.PhoneType.MOBILE;
import static dev.anilp.ecommerce_backend.entity.user.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doAnswer;

@ExtendWith(MockitoExtension.class)
class PhoneServiceImplTest {

    @Mock
    private PhoneRepository phoneRepository;

    @Mock
    private UserService userService;

    @Mock
    private PhoneMapper mapper;

    @InjectMocks
    private PhoneServiceImpl phoneService;

    private static User user;
    private static Phone phone;
    private static CreatePhoneRequestDTO createPhoneRequestDTO;

    @BeforeAll
    static void beforeAll() {
        user = new User(
                1L,
                "Test",
                "User",
                "mail@test.com",
                MALE,
                LocalDate.of(1990, 1, 1),
                new ArrayList<>(), new ArrayList<>()
        );
        phone = new Phone(
                1L,
                MOBILE,
                "5325323232",
                null
        );
        user.addPhone(phone);

        createPhoneRequestDTO = new CreatePhoneRequestDTO(
                MOBILE,
                "5325555555"
        );
    }

    @Nested
    class GetPhoneOfUserTest {
        @Test
        void itShouldReturnPhoneResponseDTOWhenPhoneExists() {
            // Given
            Long userId = 1L;
            Long phoneId = 1L;
            Phone expectedPhone = phone;
            given(phoneRepository.findPhoneByIdAndUserId(phoneId, userId)).willReturn(Optional.of(expectedPhone));
            PhoneResponseDTO expectedResponse = new PhoneResponseDTO(
                    expectedPhone.getId(),
                    expectedPhone.getPhoneType(),
                    expectedPhone.getPhoneNumber()
            );
            given(mapper.phoneToResponse(expectedPhone)).willReturn(expectedResponse);
            // When
            PhoneResponseDTO result = phoneService.getPhoneOfUser(phoneId, userId);
            // Then
            then(phoneRepository).should().findPhoneByIdAndUserId(phoneId, userId);
            then(mapper).should().phoneToResponse(expectedPhone);
            assertThat(result).isEqualTo(expectedResponse);
        }

        @Test
        void itShouldThrowExceptionWhenPhoneNotExist() {
            // Given
            Long userId = 1L;
            Long phoneId = 2L;
            given(phoneRepository.findPhoneByIdAndUserId(phoneId, userId)).willReturn(Optional.empty());
            // When/Then
            assertThatThrownBy(() -> phoneService.getPhoneOfUser(phoneId, userId))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage(String.format("PHONE not found with given PHONE ID AND USER ID: Phone id = %s, User id = %s", phoneId, userId));
        }
    }

    @Nested
    class GetAllPhoneFromUserTest {
        @Test
        void itShouldReturnListOfPhoneResponseDTOWhenGetAllPhoneFromUserAndPhonesExists() {
            // Given
            Long userId = 1L;
            Phone expectedPhone = phone;
            List<Phone> expectedList = List.of(expectedPhone);
            List<PhoneResponseDTO> expectedResponseList = List.of(new PhoneResponseDTO(
                    expectedPhone.getId(),
                    expectedPhone.getPhoneType(),
                    expectedPhone.getPhoneNumber())
            );
            given(phoneRepository.findPhonesByUserId(userId)).willReturn(expectedList);
            given(mapper.phonesToResponsesList(expectedList)).willReturn(expectedResponseList);
            // When
            List<PhoneResponseDTO> result = phoneService.getAllPhonesFromUser(userId);
            // Then
            then(phoneRepository).should().findPhonesByUserId(userId);
            then(mapper).should().phonesToResponsesList(expectedList);
            assertThat(result).isEqualTo(expectedResponseList);
        }

        @Test
        void itShouldEmptyListWhenGetAllPhonesFromUserAndPhonesNotExists() {
            // Given
            Long userId = 1L;
            given(phoneRepository.findPhonesByUserId(userId)).willReturn(new ArrayList<>());
            given(mapper.phonesToResponsesList(new ArrayList<>())).willReturn(new ArrayList<>());
            // When
            List<PhoneResponseDTO> result = phoneService.getAllPhonesFromUser(userId);
            // Then
            then(phoneRepository).should().findPhonesByUserId(userId);
            then(mapper).should().phonesToResponsesList(new ArrayList<>());
            assertThat(result).isEmpty();
        }
    }

    @Nested
    class AddPhoneToUserTest {
        @Test
        void itShouldAddNewPhoneToUserWhenPhoneNotExists() {
            // Given
            Long userId = 1L;
            CreatePhoneRequestDTO requestDTO = createPhoneRequestDTO;
            User expectedUser = user;
            given(userService.findUserById(userId)).willReturn(expectedUser);
            given(phoneRepository.existsByPhoneNumber(requestDTO.phoneNumber())).willReturn(false);
            Phone newPhone = new Phone(
                    null,
                    requestDTO.phoneType(),
                    requestDTO.phoneNumber(),
                    null);
            given(mapper.requestToPhone(requestDTO)).willReturn(newPhone
            );
            // When
            phoneService.addPhoneToUser(userId, requestDTO);
            // Then
            then(userService).should().findUserById(userId);
            then(phoneRepository).should().existsByPhoneNumber(requestDTO.phoneNumber());
            then(mapper).should().requestToPhone(requestDTO);
            assertThat(expectedUser.getPhones().getLast()).isEqualTo(newPhone);
        }

        @Test
        void itShouldThrowExceptionWhenPhoneAlreadyExists() {
            // Given
            Long userId = 1L;
            CreatePhoneRequestDTO requestDTO = createPhoneRequestDTO;
            User expectedUser = user;
            given(userService.findUserById(userId)).willReturn(expectedUser);
            given(phoneRepository.existsByPhoneNumber(requestDTO.phoneNumber())).willReturn(true);
            // When/Then
            assertThatThrownBy(() -> phoneService.addPhoneToUser(userId, requestDTO))
                    .isInstanceOf(DuplicateResourceException.class)
                    .hasMessage(String.format("PHONE with given PHONE NUMBER is already exists: %s", requestDTO.phoneNumber()));
        }
    }

    @Nested
    class UpdatePhoneOfUserTest {
        @Test
        void itShouldUpdatePhoneWhenPhoneExistsAndPhoneNumberNotChanged() {
            // Given
            Long userId = 1L;
            Long phoneId = 1L;
            Phone expectedPhone = phone;
            UpdatePhoneRequestDTO requestDTO = new UpdatePhoneRequestDTO(
                    HOME,
                    expectedPhone.getPhoneNumber());
            given(phoneRepository.findPhoneByIdAndUserId(phoneId, userId)).willReturn(Optional.of(expectedPhone));
            doAnswer(updatePhoneFromDTO -> {
                expectedPhone.setPhoneType(requestDTO.phoneType());
                return null;
            }).when(mapper).updatePhoneFromDto(expectedPhone, requestDTO);
            // When
            phoneService.updatePhoneOfUser(phoneId, userId, requestDTO);
            // Then
            then(phoneRepository).should().findPhoneByIdAndUserId(phoneId, userId);
            then(mapper).should().updatePhoneFromDto(expectedPhone, requestDTO);
            assertThat(expectedPhone.getPhoneType()).isEqualTo(requestDTO.phoneType());
            assertThat(expectedPhone.getPhoneNumber()).isEqualTo(requestDTO.phoneNumber());
        }

        @Test
        void itShouldUpdatePhoneWhenPhoneExistsAndUpdatedPhoneNumberNotExists() {
            // Given
            Long userId = 1L;
            Long phoneId = 1L;
            Phone expectedPhone = phone;
            UpdatePhoneRequestDTO requestDTO = new UpdatePhoneRequestDTO(
                    HOME,
                    "5554443322");
            given(phoneRepository.findPhoneByIdAndUserId(phoneId, userId)).willReturn(Optional.of(expectedPhone));
            given(phoneRepository.existsByPhoneNumber(requestDTO.phoneNumber())).willReturn(false);
            doAnswer(updatePhoneFromDTO -> {
                expectedPhone.setPhoneType(requestDTO.phoneType());
                expectedPhone.setPhoneNumber(requestDTO.phoneNumber());
                return null;
            }).when(mapper).updatePhoneFromDto(expectedPhone, requestDTO);
            // When
            phoneService.updatePhoneOfUser(phoneId, userId, requestDTO);
            // Then
            then(phoneRepository).should().findPhoneByIdAndUserId(phoneId, userId);
            then(phoneRepository).should().existsByPhoneNumber(requestDTO.phoneNumber());
            then(mapper).should().updatePhoneFromDto(expectedPhone, requestDTO);
            assertThat(expectedPhone.getPhoneType()).isEqualTo(requestDTO.phoneType());
            assertThat(expectedPhone.getPhoneNumber()).isEqualTo(requestDTO.phoneNumber());
        }

        @Test
        void itShouldThrowExceptionWhenPhoneExistsAndUpdatedPhoneNumberAlreadyExists() {
            // Given
            Long userId = 1L;
            Long phoneId = 1L;
            Phone expectedPhone = phone;
            UpdatePhoneRequestDTO requestDTO = new UpdatePhoneRequestDTO(
                    HOME,
                    "5554443322");
            given(phoneRepository.findPhoneByIdAndUserId(phoneId, userId)).willReturn(Optional.of(expectedPhone));
            given(phoneRepository.existsByPhoneNumber(requestDTO.phoneNumber())).willReturn(true);
            // When/ Then
            assertThatThrownBy(() -> phoneService.updatePhoneOfUser(phoneId, userId, requestDTO))
                    .isInstanceOf(DuplicateResourceException.class)
                    .hasMessage(String.format("PHONE with given PHONE NUMBER is already exists: %s", requestDTO.phoneNumber()));
        }

        @Test
        void itShouldThrowExceptionWhenPhoneDoesNotExists() {
            // Given
            Long userId = 1L;
            Long phoneId = 1L;
            UpdatePhoneRequestDTO requestDTO = new UpdatePhoneRequestDTO(
                    HOME,
                    "5554443322");
            given(phoneRepository.findPhoneByIdAndUserId(phoneId, userId)).willReturn(Optional.empty());
            // When/ Then
            assertThatThrownBy(() -> phoneService.updatePhoneOfUser(phoneId, userId, requestDTO))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage(String.format("PHONE not found with given PHONE ID AND USER ID: Phone id = %s, User id = %s", phoneId, userId));
        }
    }

    @Nested
    class DeletePhoneFromUserTest {
        @Test
        void itShouldDeletePhoneWhenPhoneExists() {
            // Given
            Long userId = 1L;
            Long phoneId = 1L;
            Phone expectedPhone = phone;
            given(phoneRepository.findPhoneByIdAndUserId(phoneId, userId)).willReturn(Optional.of(expectedPhone));
            // When
            phoneService.deletePhoneFromUser(phoneId, userId);
            // Then
            then(phoneRepository).should().findPhoneByIdAndUserId(phoneId, userId);
            then(phoneRepository).should().delete(expectedPhone);
        }

        @Test
        void itShouldThrowExceptionWhenPhoneNotExists() {
            // Given
            Long userId = 1L;
            Long phoneId = 1L;
            given(phoneRepository.findPhoneByIdAndUserId(phoneId, userId)).willReturn(Optional.empty());
            // When/ Then
            assertThatThrownBy(() -> phoneService.deletePhoneFromUser(phoneId, userId))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage(String.format("PHONE not found with given PHONE ID AND USER ID: Phone id = %s, User id = %s", phoneId, userId));
        }
    }
}
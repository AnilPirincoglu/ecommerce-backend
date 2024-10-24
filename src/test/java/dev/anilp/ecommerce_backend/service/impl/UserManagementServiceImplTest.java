package dev.anilp.ecommerce_backend.service.impl;

import dev.anilp.ecommerce_backend.dto.phone.PhoneResponseDTO;
import dev.anilp.ecommerce_backend.dto.user.UserResponseDTO;
import dev.anilp.ecommerce_backend.entity.phone.Phone;
import dev.anilp.ecommerce_backend.entity.phone.PhoneType;
import dev.anilp.ecommerce_backend.entity.user.User;
import dev.anilp.ecommerce_backend.exception.exception_class.ResourceNotFoundException;
import dev.anilp.ecommerce_backend.mapper.UserMapper;
import dev.anilp.ecommerce_backend.repository.UserRepository;
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

import static dev.anilp.ecommerce_backend.entity.user.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserManagementServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserManagementServiceImpl userManagementService;

    private static User user;
    private static UserResponseDTO userResponseDTO;

    @BeforeAll
    static void beforeAll() {
        user = new User(
                1L,
                "Test First Name",
                "Test Last Name",
                "test@email.com",
                MALE,
                LocalDate.of(1990, 1, 1),
                new ArrayList<>(), new ArrayList<>(), null
        );
        Phone phone = new Phone(1L,
                PhoneType.MOBILE,
                "5325323232", null
        );
        user.addPhone(phone);

        userResponseDTO = new UserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getGender(),
                user.getDateOfBirth(),
                List.of(new PhoneResponseDTO(
                        phone.getId(),
                        phone.getPhoneType(),
                        phone.getPhoneNumber()
                )), new ArrayList<>()
        );
    }

    @Nested
    class GetUserByIdTest {
        @Test
        void itShouldReturnUserResponseDTOWhenUserExists() {
            // Given
            Long userId = 1L;
            User expectedUser = user;
            UserResponseDTO expectedUserResponseDTO = userResponseDTO;
            given(userRepository.findById(userId)).willReturn(Optional.of(expectedUser));
            given(mapper.userToResponse(expectedUser)).willReturn(expectedUserResponseDTO);
            // When
            UserResponseDTO result = userManagementService.getUserById(userId);
            // Then
            then(userRepository).should().findById(userId);
            then(mapper).should().userToResponse(expectedUser);
            assertThat(result).isEqualTo(expectedUserResponseDTO);
        }

        @Test
        void itShouldThrowExceptionWhenUserNotExist() {
            // Given
            Long userId = 1L;
            given(userRepository.findById(userId)).willReturn(Optional.empty());
            // When/ Then
            assertThatThrownBy(() -> userManagementService.getUserById(userId))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage(String.format("USER not found with given ID: %s", userId));
            then(mapper).shouldHaveNoInteractions();
        }
    }

    @Nested
    class GetUserByEmailTest {
        @Test
        void itShouldReturnUserResponseDTOWhenUserExists() {
            // Given
            String email = "test@email.com";
            User expectedUser = user;
            UserResponseDTO expectedUserResponseDTO = userResponseDTO;
            given(userRepository.findByEmail(email)).willReturn(Optional.of(expectedUser));
            given(mapper.userToResponse(expectedUser)).willReturn(expectedUserResponseDTO);
            // When
            UserResponseDTO result = userManagementService.getUserByEmail(email);
            // Then
            then(userRepository).should().findByEmail(email);
            then(mapper).should().userToResponse(expectedUser);
            assertThat(result).isEqualTo(expectedUserResponseDTO);
        }

        @Test
        void itShouldThrowExceptionWhenUserNotExist() {
            // Given
            String email = "fail@email.com";
            given(userRepository.findByEmail(email)).willReturn(Optional.empty());
            // When/ Then
            assertThatThrownBy(() -> userManagementService.getUserByEmail(email))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage(String.format("USER not found with given EMAIL: %s", email));
            then(mapper).shouldHaveNoInteractions();
        }
    }

    @Nested
    class GetUserByPhoneNumberTest {
        @Test
        void itShouldReturnUserResponseDTOWhenUserExists() {
            // Given
            String phoneNumber = "5325323232";
            User expectedUser = user;
            UserResponseDTO expectedUserResponseDTO = userResponseDTO;
            given(userRepository.findByPhoneNumber(phoneNumber)).willReturn(Optional.of(expectedUser));
            given(mapper.userToResponse(expectedUser)).willReturn(expectedUserResponseDTO);
            // When
            UserResponseDTO result = userManagementService.getUserByPhoneNumber(phoneNumber);
            // Then
            then(userRepository).should().findByPhoneNumber(phoneNumber);
            then(mapper).should().userToResponse(expectedUser);
            assertThat(result).isEqualTo(expectedUserResponseDTO);
        }

        @Test
        void itShouldThrowExceptionWhenUserNotExist() {
            // Given
            String phoneNumber = "5321112233";
            given(userRepository.findByPhoneNumber(phoneNumber)).willReturn(Optional.empty());
            // When/ Then
            assertThatThrownBy(() -> userManagementService.getUserByPhoneNumber(phoneNumber))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage(String.format("USER not found with given PHONE NUMBER: %s", phoneNumber));
            then(mapper).shouldHaveNoInteractions();
        }
    }

    @Nested
    class GetAllUsersTest {
        @Test
        void itShouldReturnListOfUserResponseDTOWhenUsersExist() {
            // Given
            List<User> expectedUsers = List.of(user);
            List<UserResponseDTO> expectedUserResponseDTOs = List.of(userResponseDTO);
            given(userRepository.findAll()).willReturn(expectedUsers);
            given(mapper.usersToResponsesList(expectedUsers)).willReturn(expectedUserResponseDTOs);
            // When
            List<UserResponseDTO> result = userManagementService.getAllUsers();
            // Then
            then(userRepository).should().findAll();
            then(mapper).should().usersToResponsesList(expectedUsers);
            assertThat(result).isEqualTo(expectedUserResponseDTOs);
        }

        @Test
        void itShouldReturnEmptyListWhenUsersNotExist() {
            // Given
            given(userRepository.findAll()).willReturn(new ArrayList<>());
            given(mapper.usersToResponsesList(new ArrayList<>())).willReturn(new ArrayList<>());
            // When
            List<UserResponseDTO> result = userManagementService.getAllUsers();
            // Then
            then(userRepository).should().findAll();
            then(mapper).should().usersToResponsesList(new ArrayList<>());
            assertThat(result).isEmpty();
        }
    }
}
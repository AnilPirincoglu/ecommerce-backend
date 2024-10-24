package dev.anilp.ecommerce_backend.service.impl;

import dev.anilp.ecommerce_backend.dto.user.CreateUserRequestDTO;
import dev.anilp.ecommerce_backend.dto.user.UpdateUserRequestDTO;
import dev.anilp.ecommerce_backend.entity.user.User;
import dev.anilp.ecommerce_backend.exception.exception_class.DuplicateResourceException;
import dev.anilp.ecommerce_backend.exception.exception_class.ResourceNotFoundException;
import dev.anilp.ecommerce_backend.mapper.UserMapper;
import dev.anilp.ecommerce_backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static dev.anilp.ecommerce_backend.entity.user.Gender.FEMALE;
import static dev.anilp.ecommerce_backend.entity.user.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Captor
    private ArgumentCaptor<User> userCaptor;
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private static User defaultUser;
    private static CreateUserRequestDTO createUserRequestDTO;
    private static UpdateUserRequestDTO updateUserRequestDTO;

    @BeforeAll
    static void beforeAll() {
        defaultUser = new User(
                1L,
                "Test",
                "User",
                "mail@test.com",
                MALE,
                LocalDate.of(1990, 1, 1),
                new ArrayList<>(), new ArrayList<>(), null
        );
        createUserRequestDTO = new CreateUserRequestDTO(
                "create",
                "request",
                "dto@test.com",
                FEMALE,
                LocalDate.of(2000, 1, 1)
        );
        updateUserRequestDTO = new UpdateUserRequestDTO(
                "update",
                "request",
                "update@test.com",
                FEMALE,
                LocalDate.of(2000, 1, 1)
        );
    }

    @Nested
    class FindUserByIdTest {
        @Test
        void itShouldReturnUserWhenUserExists() {
            // Given
            Long userId = 1L;
            User expectedUser = defaultUser;
            given(userRepository.findById(userId)).willReturn(Optional.of(expectedUser));
            // When
            User user = userService.findUserById(userId);
            // Then
            then(userRepository).should().findById(userId);
            assertThat(user).isEqualTo(expectedUser);
        }

        @Test
        void itShouldThrowExceptionWhenUserNotExists() {
            // Given
            Long userId = 2L;
            given(userRepository.findById(userId)).willReturn(Optional.empty());
            // When/Then
            assertThatThrownBy(() -> userService.findUserById(userId))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage(String.format("USER not found with given ID: %s", userId));
        }
    }

    @Nested
    class SaveUserTest {
        @Test
        void itShouldSaveNewUserWhenEmailNotExists() {
            // Given
            CreateUserRequestDTO requestDTO = createUserRequestDTO;

            User createdUser = new User(null,
                    requestDTO.firstName(),
                    requestDTO.lastName(),
                    requestDTO.email(),
                    requestDTO.gender(),
                    requestDTO.dateOfBirth(),
                    new ArrayList<>(), new ArrayList<>(), null);

            given(userRepository.existsByEmail(requestDTO.email())).willReturn(false);
            given(userMapper.requestToUser(requestDTO)).willReturn(createdUser);
            // When
            userService.saveUser(requestDTO);
            // Then
            then(userRepository).should().existsByEmail(requestDTO.email());
            then(userMapper).should().requestToUser(requestDTO);
            then(userRepository).should().save(createdUser);
        }

        @Test
        void itShouldThrowExceptionWhenEmailAlreadyExists() {
            // Given
            CreateUserRequestDTO requestDTO = createUserRequestDTO;

            given(userRepository.existsByEmail(requestDTO.email())).willReturn(true);
            // When/Then
            assertThatThrownBy(() -> userService.saveUser(requestDTO))
                    .isInstanceOf(DuplicateResourceException.class)
                    .hasMessage(String.format("USER with given EMAIL is already exists: %s", requestDTO.email()));
        }
    }

    @Nested
    class UpdateUserById {
        @Test
        void itShouldUpdateUserWhenUserExistsAndEmailNotChanged() {
            // Given
            Long userId = 1L;
            User expectedUser = defaultUser;
            given(userRepository.findById(userId)).willReturn(Optional.of(expectedUser));

            UpdateUserRequestDTO requestDTO = new UpdateUserRequestDTO(
                    "update",
                    "request",
                    expectedUser.getEmail(),
                    FEMALE,
                    LocalDate.of(2000, 1, 1));
            doAnswer(updateUserFromDto -> {
                expectedUser.setFirstName(requestDTO.firstName());
                expectedUser.setLastName(requestDTO.lastName());
                expectedUser.setGender(requestDTO.gender());
                expectedUser.setDateOfBirth(requestDTO.dateOfBirth());
                return null;
            }).when(userMapper).updateUserFromDto(expectedUser, requestDTO);
            // When
            userService.updateUserById(userId, requestDTO);
            // Then
            then(userRepository).should().findById(userId);
            then(userRepository).should(never()).existsByEmail(requestDTO.email());
            then(userMapper).should().updateUserFromDto(expectedUser, requestDTO);
            then(userRepository).should().save(userCaptor.capture());
            User updatedUser = userCaptor.getValue();
            assertThat(updatedUser.getFirstName()).isEqualTo(requestDTO.firstName());
            assertThat(updatedUser.getLastName()).isEqualTo(requestDTO.lastName());
            assertThat(updatedUser.getEmail()).isEqualTo(requestDTO.email());
            assertThat(updatedUser.getGender()).isEqualTo(requestDTO.gender());
            assertThat(updatedUser.getDateOfBirth()).isEqualTo(requestDTO.dateOfBirth());
        }

        @Test
        void itShouldUpdateUserWhenUserExistsAndUpdatedEmailNotExists() {
            // Given
            Long userId = 1L;
            User expectedUser = defaultUser;
            given(userRepository.findById(userId)).willReturn(Optional.of(expectedUser));

            UpdateUserRequestDTO requestDTO = updateUserRequestDTO;
            given(userRepository.existsByEmail(requestDTO.email())).willReturn(false);

            doAnswer(updateUserFromDto -> {
                expectedUser.setFirstName(requestDTO.firstName());
                expectedUser.setLastName(requestDTO.lastName());
                expectedUser.setEmail(requestDTO.email());
                expectedUser.setGender(requestDTO.gender());
                expectedUser.setDateOfBirth(requestDTO.dateOfBirth());
                return null;
            }).when(userMapper).updateUserFromDto(expectedUser, requestDTO);
            // When
            userService.updateUserById(userId, requestDTO);
            // Then
            then(userRepository).should().findById(userId);
            then(userRepository).should().existsByEmail(requestDTO.email());
            then(userMapper).should().updateUserFromDto(expectedUser, requestDTO);
            then(userRepository).should().save(userCaptor.capture());
            User updatedUser = userCaptor.getValue();
            assertThat(updatedUser.getFirstName()).isEqualTo(requestDTO.firstName());
            assertThat(updatedUser.getLastName()).isEqualTo(requestDTO.lastName());
            assertThat(updatedUser.getEmail()).isEqualTo(requestDTO.email());
            assertThat(updatedUser.getGender()).isEqualTo(requestDTO.gender());
            assertThat(updatedUser.getDateOfBirth()).isEqualTo(requestDTO.dateOfBirth());
        }

        @Test
        void itShouldThrowExceptionWhenUserNotExists() {
            // Given
            Long userId = 1L;
            given(userRepository.findById(userId)).willReturn(Optional.empty());
            UpdateUserRequestDTO requestDTO = updateUserRequestDTO;
            // When/Then
            assertThatThrownBy(() -> userService.updateUserById(userId, requestDTO))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage(String.format("USER not found with given ID: %s", userId));
        }

        @Test
        void itShouldThrowExceptionWhenUserExistsAndUpdatedEmailAlreadyExists() {
            // Given
            Long userId = 1L;
            User expectedUser = defaultUser;
            given(userRepository.findById(userId)).willReturn(Optional.of(expectedUser));

            UpdateUserRequestDTO requestDTO = updateUserRequestDTO;
            given(userRepository.existsByEmail(requestDTO.email())).willReturn(true);
            // When/Then
            assertThatThrownBy(() -> userService.updateUserById(userId, requestDTO))
                    .isInstanceOf(DuplicateResourceException.class)
                    .hasMessage(String.format("USER with given EMAIL is already exists: %s", requestDTO.email()));
        }
    }

    @Nested
    class DeleteUserByIdTest {
        @Test
        void itShouldDeleteUserWhenUserExists() {
            // Given
            Long userId = 1L;
            User expectedUser = defaultUser;
            given(userRepository.findById(userId)).willReturn(Optional.of(expectedUser));
            // When
            userService.deleteUserById(userId);
            // Then
            then(userRepository).should().findById(userId);
            then(userRepository).should().delete(expectedUser);
        }

        @Test
        void itShouldThrowExceptionWhenUserNotExists() {
            // Given
            Long userId = 2L;
            given(userRepository.findById(userId)).willReturn(Optional.empty());
            // When/Then
            assertThatThrownBy(() -> userService.deleteUserById(userId))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage(String.format("USER not found with given ID: %s", userId));
        }
    }
}
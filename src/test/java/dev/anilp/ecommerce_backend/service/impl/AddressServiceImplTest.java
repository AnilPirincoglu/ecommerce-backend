package dev.anilp.ecommerce_backend.service.impl;

import dev.anilp.ecommerce_backend.dto.address.AddressResponseDTO;
import dev.anilp.ecommerce_backend.dto.address.CreateAddressRequestDTO;
import dev.anilp.ecommerce_backend.dto.address.UpdateAddressRequestDTO;
import dev.anilp.ecommerce_backend.entity.address.Address;
import dev.anilp.ecommerce_backend.entity.user.User;
import dev.anilp.ecommerce_backend.exception.exception_class.ResourceNotFoundException;
import dev.anilp.ecommerce_backend.mapper.AddressMapper;
import dev.anilp.ecommerce_backend.repository.AddressRepository;
import dev.anilp.ecommerce_backend.service.UserService;
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
import java.util.List;
import java.util.Optional;

import static dev.anilp.ecommerce_backend.entity.address.AddressType.HOME;
import static dev.anilp.ecommerce_backend.entity.user.Gender.MALE;
import static dev.anilp.ecommerce_backend.exception.ExceptionConstant.ID;
import static dev.anilp.ecommerce_backend.exception.ExceptionConstant.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doAnswer;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Captor
    private ArgumentCaptor<Address> addressCaptor;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private UserService userService;

    @Mock
    private AddressMapper mapper;

    @InjectMocks
    private AddressServiceImpl addressService;

    private static User user;
    private static Address address;
    private static CreateAddressRequestDTO createAddressRequestDTO;
    private static UpdateAddressRequestDTO updateAddressRequestDTO;

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
        address = new Address(
                1L,
                HOME,
                "Test Address Line",
                "Test Street",
                "Test District",
                "Test City",
                "34125",
                null
        );
        user.addAddress(address);

        createAddressRequestDTO = new CreateAddressRequestDTO(
                HOME,
                "Request Address Line",
                "Request Street",
                "Request District",
                "Request City",
                "35255"
        );

        updateAddressRequestDTO = new UpdateAddressRequestDTO(
                HOME,
                "Updated Address Line",
                "Updated Street",
                "Updated District",
                "Updated City",
                "35255"
        );
    }

    @Nested
    class GetAddressOfUserByIdTest {
        @Test
        void itShouldReturnAddressResponseDtoWhenAddressExists() {
            // Given
            Long addressId = 1L;
            Long userId = 1L;
            Address expectedAddress = address;
            AddressResponseDTO expectedResponse = new AddressResponseDTO(
                    null,
                    expectedAddress.getAddressType(),
                    expectedAddress.getCity(),
                    expectedAddress.getPostalCode(),
                    expectedAddress.getDistrict(),
                    expectedAddress.getStreet(),
                    expectedAddress.getAddressLine()
            );
            given(addressRepository.findAddressByIdAndUserId(addressId, userId))
                    .willReturn(Optional.of(expectedAddress));
            given(mapper.addressToResponse(address)).willReturn(expectedResponse);
            // When
            AddressResponseDTO result = addressService.getAddressOfUserById(userId, addressId);
            // Then
            then(addressRepository).should().findAddressByIdAndUserId(addressId, userId);
            then(mapper).should().addressToResponse(expectedAddress);
            assertThat(result).isEqualTo(expectedResponse);
        }

        @Test
        void itShouldThrowExceptionWhenAddressNotExists() {
            // Given
            Long addressId = 2L;
            Long userId = 1L;
            given(addressRepository.findAddressByIdAndUserId(addressId, userId))
                    .willReturn(Optional.empty());
            // When/Then
            assertThatThrownBy(() -> addressService.getAddressOfUserById(addressId, userId))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage(String.format(
                            "ADDRESS not found with given ADDRESS ID AND USER ID: Address id = %s, User id = %s", addressId, userId));
        }
    }

    @Nested
    class GetAllAddressesOfUserTest {
        @Test
        void itShouldReturnListOfAddressResponseDTOWhenAddressesExists() {
            // Given
            Long userId = 1L;
            Address expectedAddress = address;
            List<Address> expectedAddressList = List.of(expectedAddress);
            AddressResponseDTO expectedResponse = new AddressResponseDTO(
                    null,
                    expectedAddress.getAddressType(),
                    expectedAddress.getCity(),
                    expectedAddress.getPostalCode(),
                    expectedAddress.getDistrict(),
                    expectedAddress.getStreet(),
                    expectedAddress.getAddressLine()
            );
            List<AddressResponseDTO> expectedResponseList = List.of(expectedResponse);
            given(addressRepository.findAllAddressesByUserId(userId)).willReturn(expectedAddressList);
            given(mapper.addressesToResponsesList(expectedAddressList)).willReturn(expectedResponseList);
            // When
            List<AddressResponseDTO> result = addressService.getAllAddressesOfUser(userId);
            // Then
            then(addressRepository).should().findAllAddressesByUserId(userId);
            then(mapper).should().addressesToResponsesList(expectedAddressList);
            assertThat(result).isEqualTo(expectedResponseList);
        }

        @Test
        void itShouldReturnEmptyListWhenAddressesNotExist() {
            // Given
            Long userId = 1L;
            given(addressRepository.findAllAddressesByUserId(userId)).willReturn(List.of());
            given(mapper.addressesToResponsesList(List.of())).willReturn(new ArrayList<>());
            // When
            List<AddressResponseDTO> result = addressService.getAllAddressesOfUser(userId);
            // Then
            then(addressRepository).should().findAllAddressesByUserId(userId);
            then(mapper).should().addressesToResponsesList(List.of());
            assertThat(result).isEmpty();
        }
    }

    @Nested
    class AddAddressToUserTest {
        @Test
        void itShouldAddNewAddressToUserWhenUserExists() {
            // Given
            Long userId = 1L;
            User expectedUser = user;
            CreateAddressRequestDTO requestDTO = createAddressRequestDTO;
            Address createdAddress = new Address(
                    null,
                    requestDTO.addressType(),
                    requestDTO.addressLine(),
                    requestDTO.street(),
                    requestDTO.district(),
                    requestDTO.city(),
                    requestDTO.postalCode(),
                    null
            );
            given(userService.findUserById(userId)).willReturn(expectedUser);
            given(mapper.requestToAddress(requestDTO))
                    .willReturn(createdAddress);
            // When
            addressService.addAddressToUser(userId, requestDTO);
            // Then
            then(userService).should().findUserById(userId);
            then(mapper).should().requestToAddress(requestDTO);
            then(addressRepository).should().save(createdAddress);
            assertThat(expectedUser.getAddresses().getLast()).isEqualTo(createdAddress);
        }

        @Test
        void itShouldThrowExceptionWhenUserNotExists() {
            // Given
            Long userId = 2L;
            CreateAddressRequestDTO requestDTO = createAddressRequestDTO;
            given(userService.findUserById(userId))
                    .willThrow(new ResourceNotFoundException(USER, ID, userId));

            // When/Then
            assertThatThrownBy(() -> addressService.addAddressToUser(userId, requestDTO))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage(String.format("USER not found with given ID: %s", userId));

            then(userService).should().findUserById(userId);
            then(mapper).shouldHaveNoInteractions();
            then(addressRepository).shouldHaveNoInteractions();
        }
    }

    @Nested
    class UpdateAddressOfUserTest {
        @Test
        void itShouldUpdateAddressWhenAddressExists() {
            // Given
            Long userId = 1L;
            Long addressId = 1L;
            Address expectedAddress = address;
            UpdateAddressRequestDTO requestDTO = updateAddressRequestDTO;
            given(addressRepository.findAddressByIdAndUserId(addressId, userId))
                    .willReturn(Optional.of(expectedAddress));
            doAnswer(updateAddressFromDTO -> {
                expectedAddress.setAddressType(requestDTO.addressType());
                expectedAddress.setAddressLine(requestDTO.addressLine());
                expectedAddress.setStreet(requestDTO.street());
                expectedAddress.setDistrict(requestDTO.district());
                expectedAddress.setCity(requestDTO.city());
                expectedAddress.setPostalCode(requestDTO.postalCode());
                return null;
            }).when(mapper).updateAddressFromDto(expectedAddress, requestDTO);
            // When
            addressService.updateAddressOfUser(userId, addressId, requestDTO);
            // Then
            then(addressRepository).should().findAddressByIdAndUserId(addressId, userId);
            then(mapper).should().updateAddressFromDto(expectedAddress, requestDTO);
            then(addressRepository).should().save(addressCaptor.capture());
            Address updatedAddress = addressCaptor.getValue();

            assertThat(updatedAddress).isEqualTo(expectedAddress);
            assertThat(updatedAddress.getAddressType()).isEqualTo(requestDTO.addressType());
            assertThat(updatedAddress.getAddressLine()).isEqualTo(requestDTO.addressLine());
            assertThat(updatedAddress.getStreet()).isEqualTo(requestDTO.street());
            assertThat(updatedAddress.getDistrict()).isEqualTo(requestDTO.district());
            assertThat(updatedAddress.getCity()).isEqualTo(requestDTO.city());
            assertThat(updatedAddress.getPostalCode()).isEqualTo(requestDTO.postalCode());
        }

        @Test
        void itShouldThrowExceptionWhenAddressNotExists() {
            // Given
            Long userId = 1L;
            Long addressId = 2L;
            UpdateAddressRequestDTO requestDTO = updateAddressRequestDTO;
            given(addressRepository.findAddressByIdAndUserId(addressId, userId))
                    .willReturn(Optional.empty());
            // When/Then
            assertThatThrownBy(() -> addressService.updateAddressOfUser(addressId, userId, requestDTO))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage(String.format("ADDRESS not found with given ADDRESS ID AND USER ID: Address id = %s, User id = %s", addressId, userId));
        }
    }

    @Nested
    class DeleteAddressFromUserTest {
        @Test
        void itShouldDeleteAddressWhenAddressExists() {
            // Given
            Long userId = 1L;
            Long addressId = 1L;
            Address expectedAddress = address;
            given(addressRepository.findAddressByIdAndUserId(addressId, userId))
                    .willReturn(Optional.of(expectedAddress));
            // When
            addressService.deleteAddressFromUser(addressId, userId);
            // Then
            then(addressRepository).should().findAddressByIdAndUserId(addressId, userId);
            then(addressRepository).should().delete(expectedAddress);
        }

        @Test
        void itShouldThrowExceptionWhenAddressNotExists() {
            // Given
            Long userId = 1L;
            Long addressId = 2L;
            given(addressRepository.findAddressByIdAndUserId(addressId, userId))
                    .willReturn(Optional.empty());
            // When/Then
            assertThatThrownBy(() -> addressService.deleteAddressFromUser(addressId, userId))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage(String.format("ADDRESS not found with given ADDRESS ID AND USER ID: Address id = %s, User id = %s", addressId, userId));
        }
    }
}
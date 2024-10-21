package dev.anilp.ecommerce_backend.repository;

import dev.anilp.ecommerce_backend.entity.address.Address;
import dev.anilp.ecommerce_backend.entity.user.User;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static dev.anilp.ecommerce_backend.entity.address.AddressType.HOME;
import static dev.anilp.ecommerce_backend.entity.user.Gender.FEMALE;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
})
class AddressRepositoryTest {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Flyway flyway;

    private Address address;
    private User userWithAddress;
    private User userWithoutAddress;

    @BeforeEach
    void setUp() {
        flyway.clean();
        flyway.migrate();
        userWithAddress = new User(null, "Test First Name", "Test Last Name", "test@test.com", FEMALE,
                LocalDate.of(1990, 1, 1), new ArrayList<>(), new ArrayList<>());
        address = new Address(null, HOME, "Test Address Line", "Test Street", "Test District",
                "Test City", "34100", null);
        userWithAddress.addAddress(address);
        addressRepository.save(address);

        userWithoutAddress = new User(null, "No Phone", "User", "no_phone@test.com", FEMALE,
                LocalDate.of(1990, 1, 1), new ArrayList<>(), new ArrayList<>());
        userRepository.save(userWithoutAddress);
    }

    @Nested
    class FindAddressByIdAndUserIdTest {
        @Test
        void itShouldReturnPresentOptionalAddressWhenAddressIsExists() {
            //Given
            Long addressId = address.getId();
            Long userId = userWithAddress.getId();
            // When
            Optional<Address> result = addressRepository.findAddressByIdAndUserId(addressId, userId);
            // Then
            assertThat(result).isPresent()
                    .contains(address);
        }

        @Test
        void itShouldReturnEmptyOptionalAddressWhenAddressIsNotExists() {
            // Given
            Long addressId = address.getId();
            Long userId = userWithoutAddress.getId();
            // When
            Optional<Address> result = addressRepository.findAddressByIdAndUserId(addressId, userId);
            // Then
            assertThat(result).isEmpty();
        }
    }

    @Nested
    class FindAllAddressesByUserIdTest {
        @Test
        void itShouldReturnListOfAddressesWhenAddressesExists() {
            // Given
            Long userId = userWithAddress.getId();
            // When
            List<Address> result = addressRepository.findAllAddressesByUserId(userId);
            // Then
            assertThat(result).isNotEmpty()
                    .containsExactly(address);
        }

        @Test
        void itShouldReturnEmptyListWhenAddressesNotExists() {
            // Given
            Long userId = userWithoutAddress.getId();
            // When
            List<Address> result = addressRepository.findAllAddressesByUserId(userId);
            // Then
            assertThat(result).isEmpty();
        }
    }
}
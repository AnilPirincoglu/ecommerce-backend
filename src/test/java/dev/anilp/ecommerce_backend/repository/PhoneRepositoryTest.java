package dev.anilp.ecommerce_backend.repository;

import dev.anilp.ecommerce_backend.entity.phone.Phone;
import dev.anilp.ecommerce_backend.entity.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static dev.anilp.ecommerce_backend.entity.phone.PhoneType.MOBILE;
import static dev.anilp.ecommerce_backend.entity.user.Gender.FEMALE;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.properties.jakarta.persistence.validation.mode=none",
        "spring.flyway.enabled=false",
        "spring.jpa.hibernate.ddl-auto=update"
})
class PhoneRepositoryTest {
    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private UserRepository userRepository;

    private User userWithPhone;
    private Phone phone;
    private User userWithoutPhone;

    @BeforeEach
    void setUp() {
        userWithPhone = new User(
                null,
                "Test First Name",
                "Test Last Name",
                "withphone@test.com",
                FEMALE,
                LocalDate.of(1990, 1, 1),
                new ArrayList<>(), new ArrayList<>(), null
        );
        phone = new Phone(
                null,
                MOBILE,
                "1112223344",
                null
        );
        userWithPhone.addPhone(phone);
        phoneRepository.save(phone);

        userWithoutPhone = new User(
                null,
                "Test First Name",
                "Test Last Name",
                "no_phone@test.com",
                FEMALE,
                LocalDate.of(1990, 1, 1),
                new ArrayList<>(), new ArrayList<>(),
                null
        );
        userRepository.save(userWithoutPhone);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Nested
    class ExistsByPhoneNumberTest {
        @Test
        void itShouldReturnTrueWhenPhoneNumberExists() {
            // Given
            String phoneNumber = "1112223344";
            // When
            Boolean result = phoneRepository.existsByPhoneNumber(phoneNumber);
            // Then
            assertThat(result).isTrue();
        }

        @Test
        void itShouldReturnFalseWhenPhoneNumberNotExists() {
            // Given
            String phoneNumber = "1112223345";
            // When
            Boolean result = phoneRepository.existsByPhoneNumber(phoneNumber);
            // Then
            assertThat(result).isFalse();
        }
    }

    @Nested
    class FindPhoneByIdAndUserIdTest {
        @Test
        void itShouldReturnPresentOptionalPhoneWhenPhoneExists() {
            // Given
            Long phoneId = phone.getId();
            Long userId = userWithPhone.getId();
            // When
            Optional<Phone> result = phoneRepository.findPhoneByIdAndUserId(phoneId, userId);
            // Then
            assertThat(result).isPresent()
                    .contains(phone);
        }

        @Test
        void itShouldReturnEmptyOptionalWhenPhoneNotExists() {
            // Given
            Long phoneId = phone.getId();
            Long userId = userWithoutPhone.getId();
            // When
            Optional<Phone> result = phoneRepository.findPhoneByIdAndUserId(phoneId, userId);
            // Then
            assertThat(result).isEmpty();
        }
    }

    @Nested
    class FindPhonesByUserIdTest {
        @Test
        void itShouldReturnListOfPhonesWhenPhonesExists() {
            // Given
            Long userId = userWithPhone.getId();
            // When
            List<Phone> result = phoneRepository.findPhonesByUserId(userId);
            // Then
            assertThat(result).isNotEmpty()
                    .containsExactlyElementsOf(userWithPhone.getPhones());
        }

        @Test
        void itShouldReturnEmptyListWhenPhonesNotExists() {
            // Given
            Long userId = userWithoutPhone.getId();
            // When
            List<Phone> result = phoneRepository.findPhonesByUserId(userId);
            // Then
            assertThat(result).isEmpty();
        }
    }
}

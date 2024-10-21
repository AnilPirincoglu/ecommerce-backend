package dev.anilp.ecommerce_backend.repository;

import dev.anilp.ecommerce_backend.entity.phone.Phone;
import dev.anilp.ecommerce_backend.entity.user.User;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static dev.anilp.ecommerce_backend.entity.phone.PhoneType.MOBILE;
import static dev.anilp.ecommerce_backend.entity.user.Gender.FEMALE;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.properties.jakarta.persistence.validation.mode=none"
})
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Flyway flyway;

    private User defaultUser;

    @BeforeEach
    void setUp() {
        flyway.clean();
        flyway.migrate();
        defaultUser = new User(
                null,
                "Test First Name",
                "Test Last Name",
                "test@test.com",
                FEMALE,
                LocalDate.of(1990, 1, 1),
                new ArrayList<>(), new ArrayList<>());
        defaultUser.addPhone(new Phone(null, MOBILE, "5325323232", null));
        userRepository.save(defaultUser);
    }

    @Nested
    class ExistsByEmailTest {
        @Test
        void itShouldReturnTrueWhenEmailExists() {
            // Given
            String email = "test@test.com";
            // When
            Boolean exists = userRepository.existsByEmail(email);
            // Then
            assertThat(exists).isTrue();
        }

        @Test
        void itShouldReturnFalseWhenEmailNotExists() {
            // Given
            String email = "failTest.com";
            // When
            Boolean exists = userRepository.existsByEmail(email);
            // Then
            assertThat(exists).isFalse();
        }
    }

    @Nested
    class FindByEmailTest {
        @Test
        void itShouldReturnPresentOptionalWhenEmailExists() {
            // Given
            String email = "test@test.com";
            // When
            Optional<User> foundUser = userRepository.findByEmail(email);
            // Then
            assertThat(foundUser.isPresent()).isTrue();
            assertThat(foundUser.get()).isEqualTo(defaultUser);
        }

        @Test
        void itShouldReturnEmptyOptionalWhenEmailNotExists() {
            // Given
            String email = "fail@test.com";
            // When
            Optional<User> foundUser = userRepository.findByEmail(email);
            // Then
            assertThat(foundUser.isPresent()).isFalse();
        }
    }

    @Nested
    class FindByPhoneNumberTest {
        @Test
        void itShouldReturnPresentOptionalWhenPhoneNumberExists() {
            // Given
            String phoneNumber = "5325323232";
            // When
            Optional<User> foundUser = userRepository.findByPhoneNumber(phoneNumber);
            // Then
            assertThat(foundUser.isPresent()).isTrue();
            assertThat(foundUser.get()).isEqualTo(defaultUser);
        }

        @Test
        void itShouldReturnEmptyOptionalWhenPhoneNumberNotExists() {
            // Given
            String phoneNumber = "532532323";
            // When
            Optional<User> foundUser = userRepository.findByPhoneNumber(phoneNumber);
            // Then
            assertThat(foundUser.isPresent()).isFalse();
        }
    }
}
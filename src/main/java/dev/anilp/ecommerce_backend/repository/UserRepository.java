package dev.anilp.ecommerce_backend.repository;

import dev.anilp.ecommerce_backend.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.email = :email")
    Boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.phones p WHERE p.phoneNumber= :phoneNumber")
    Optional<User> findByPhoneNumber(String phoneNumber);
}

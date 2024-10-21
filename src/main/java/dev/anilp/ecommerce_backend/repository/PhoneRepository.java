package dev.anilp.ecommerce_backend.repository;

import dev.anilp.ecommerce_backend.entity.phone.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END FROM Phone p WHERE p.phoneNumber = :phoneNumber")
    Boolean existsByPhoneNumber(String phoneNumber);

    @Query("SELECT p From Phone p WHERE p.id = :phoneId AND p.user.id = :userId")
    Optional<Phone> findPhoneByIdAndUserId(Long phoneId, Long userId);

    @Query("SELECT p FROM Phone p WHERE p.user.id = :userId")
    List<Phone> findPhonesByUserId(Long userId);
}

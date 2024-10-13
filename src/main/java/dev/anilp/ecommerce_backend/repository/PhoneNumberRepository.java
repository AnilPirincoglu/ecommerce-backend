package dev.anilp.ecommerce_backend.repository;

import dev.anilp.ecommerce_backend.entity.phone_number.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END FROM PhoneNumber p WHERE p.phoneNumber = :phoneNumber")
    Boolean existsByPhoneNumber(String phoneNumber);
}

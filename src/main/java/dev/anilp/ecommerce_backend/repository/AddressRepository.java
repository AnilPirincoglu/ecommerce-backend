package dev.anilp.ecommerce_backend.repository;

import dev.anilp.ecommerce_backend.entity.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("SELECT a FROM Address a WHERE a.id = :addressId AND a.user.id = :userId")
    Optional<Address> findAddressByIdAndUserId(Long addressId, Long userId);

    @Query("SELECT a FROM Address a WHERE a.user.id = :userId")
    List<Address> findAllAddressesByUserId(Long userId);
}

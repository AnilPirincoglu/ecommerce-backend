package dev.anilp.ecommerce_backend.repository;

import dev.anilp.ecommerce_backend.entity.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}

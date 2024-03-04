package com.challenge.backend.ecommerce.repository;

import com.challenge.backend.ecommerce.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser,Long> {
    @Query("SELECT au FROM ApplicationUser au WHERE au.email = :email")
    Optional<ApplicationUser> findByEmail(String email);
}

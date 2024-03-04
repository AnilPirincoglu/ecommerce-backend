package com.challenge.backend.ecommerce.service;

import com.challenge.backend.ecommerce.dto.ApplicationUserRequestDto;
import com.challenge.backend.ecommerce.dto.ApplicationUserResponseDto;
import com.challenge.backend.ecommerce.entity.ApplicationUser;
import com.challenge.backend.ecommerce.entity.Role;
import com.challenge.backend.ecommerce.repository.ApplicationUserRepository;
import com.challenge.backend.ecommerce.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {

    private ApplicationUserRepository applicationUserRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(ApplicationUserRepository applicationUserRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ApplicationUserResponseDto register(ApplicationUserRequestDto applicationUserRequestDto) {

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByAuthority("USER").get());

        ApplicationUser user = new ApplicationUser();
        user = ApplicationUser.builder()
                .name(applicationUserRequestDto.name())
                .surname(applicationUserRequestDto.surname())
                .email(applicationUserRequestDto.email())
                .password(passwordEncoder.encode(applicationUserRequestDto.password()))
                .registerDate(new Date())
                .roles(roles).build();

        applicationUserRepository.save(user) ;
        return new ApplicationUserResponseDto(user.getName(), user.getSurname(), user.getEmail(), user.getRegisterDate());
    }
}

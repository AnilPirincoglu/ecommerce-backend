package com.challenge.backend.ecommerce.service;

import com.challenge.backend.ecommerce.dto.ApplicationUserResponseDto;
import com.challenge.backend.ecommerce.entity.ApplicationUser;
import com.challenge.backend.ecommerce.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;

@Service
public class ApplicationUserService implements UserDetailsService {

    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    public ApplicationUserService(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    public ApplicationUserResponseDto findUser(@PathVariable String email){
        ApplicationUser applicationUser = applicationUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User credentials are not valid!")
                );
        return new ApplicationUserResponseDto(applicationUser.getName(), applicationUser.getSurname(), applicationUser.getEmail(), new Date());
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return applicationUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User credentials are not valid!")
                );
    }
}

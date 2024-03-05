package com.challenge.backend.ecommerce.controller;

import com.challenge.backend.ecommerce.dto.ApplicationUserResponseDto;
import com.challenge.backend.ecommerce.service.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class ApplicationUserController {

    private ApplicationUserService applicationUserService;

    @Autowired
    public ApplicationUserController(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @GetMapping("/{email}")
    public ApplicationUserResponseDto findUserByEmail(@PathVariable String email){
        return applicationUserService.findUser(email);
    }
}

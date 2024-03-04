package com.challenge.backend.ecommerce.controller;

import com.challenge.backend.ecommerce.dto.ApplicationUserRequestDto;
import com.challenge.backend.ecommerce.dto.ApplicationUserResponseDto;
import com.challenge.backend.ecommerce.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ApplicationUserResponseDto register(@RequestBody ApplicationUserRequestDto applicationUserRequestDto){
        return authenticationService.register(applicationUserRequestDto);
    }
}

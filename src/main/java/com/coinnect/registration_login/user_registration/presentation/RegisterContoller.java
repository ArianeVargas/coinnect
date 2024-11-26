package com.coinnect.registration_login.user_registration.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coinnect.registration_login.user_registration.application.RegisterService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class RegisterContoller {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        RegisterResponseDTO response = registerService.register(registerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

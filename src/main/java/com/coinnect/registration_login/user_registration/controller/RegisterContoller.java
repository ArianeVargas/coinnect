package com.coinnect.registration_login.user_registration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coinnect.registration_login.user_registration.dto.RegisterRequestDTO;
import com.coinnect.registration_login.user_registration.dto.RegisterResponseDTO;
import com.coinnect.registration_login.user_registration.service.RegisterService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/register")
public class RegisterContoller {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        RegisterResponseDTO response = registerService.register(registerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

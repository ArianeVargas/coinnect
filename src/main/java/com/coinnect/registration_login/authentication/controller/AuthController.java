package com.coinnect.registration_login.authentication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coinnect.registration_login.authentication.dto.LoginRequestDTO;
import com.coinnect.registration_login.authentication.dto.TokenResponseDTO;
import com.coinnect.registration_login.authentication.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired  
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        TokenResponseDTO response = authService.login(loginRequestDTO);
        return ResponseEntity.ok(response);
    }
}

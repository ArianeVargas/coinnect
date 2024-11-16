package com.coinnect.registration_login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coinnect.registration_login.dto.LoginRequestDTO;
import com.coinnect.registration_login.dto.RegisterRequestDTO;
import com.coinnect.registration_login.dto.TokenResponseDTO;
import com.coinnect.registration_login.model.User;
import com.coinnect.registration_login.service.AuthService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody RegisterRequestDTO registerRequestDTO) {
        User userRegister = authService.register(registerRequestDTO);
        return new ResponseEntity<>(userRegister, HttpStatus.CREATED);
    }
    
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> authenticate(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(new TokenResponseDTO());
    }
}

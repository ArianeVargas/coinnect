package com.coinnect.registration_login.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coinnect.registration_login.dto.RegisterRequestDTO;
import com.coinnect.registration_login.exception.ResourceNotFoundException;
import com.coinnect.registration_login.model.User;
import com.coinnect.registration_login.repository.UserRepository;
import org.springframework.security.core.AuthenticationException;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(RegisterRequestDTO registerRequestDTO) {
        List<User> existingUsers = userRepository.findByIdentificationUser(registerRequestDTO.getIdentificationUser());
        if (!existingUsers.isEmpty()) {
            throw new ResourceNotFoundException("Usuario ya registrado con esta identificación.");
        }

        User newUser = new User();
        newUser.setNameUser(registerRequestDTO.getNameUser());
        newUser.setLastNameUser(registerRequestDTO.getLastNameUser());
        newUser.setIdentificationUser(registerRequestDTO.getIdentificationUser());
        newUser.setEmailUser(registerRequestDTO.getEmailUser());
        newUser.setUserName(registerRequestDTO.getUserName());
        newUser.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));

        return userRepository.save(newUser);
    }

    public String authenticate(String userName, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userName, password)
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return tokenService.getToken(userDetails);

        } catch (AuthenticationException e) {
            throw new ResourceNotFoundException("Credenciales incorrectas.");
        }
    }
}

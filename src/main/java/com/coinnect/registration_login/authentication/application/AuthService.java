package com.coinnect.registration_login.authentication.application;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coinnect.registration_login.authentication.presentation.LoginRequestDTO;
import com.coinnect.registration_login.authentication.presentation.TokenResponseDTO;
import com.coinnect.registration_login.common.exception.ResourceNotFoundException;
import com.coinnect.registration_login.user_registration.domain.User;
import com.coinnect.registration_login.user_registration.infraestructure.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public TokenResponseDTO login(LoginRequestDTO loginRequestDTO) {
        validateUserExistenceForLogin(loginRequestDTO); 
    
        Authentication authentication = authenticateUser(loginRequestDTO); 
    
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDTO.getUserName());
    
        String token = tokenService.getToken(userDetails);
    
        return new TokenResponseDTO(token);
    }

    private void validateUserExistenceForLogin(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByUserName(loginRequestDTO.getUserName())
                .orElseThrow(() -> new ResourceNotFoundException("El nombre de usuario no existe."));
    
        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("La contraseña es incorrecta.");
        }
    }

    private Authentication authenticateUser(LoginRequestDTO loginRequestDTO) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getUserName(),  
                        loginRequestDTO.getPassword()   
                )
        );
    }
}

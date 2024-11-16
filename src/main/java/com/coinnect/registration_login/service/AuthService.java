package com.coinnect.registration_login.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coinnect.registration_login.dto.LoginRequestDTO;
import com.coinnect.registration_login.dto.RegisterRequestDTO;
import com.coinnect.registration_login.dto.TokenResponseDTO;
import com.coinnect.registration_login.exception.ResourceNotFoundException;
import com.coinnect.registration_login.model.Role;
import com.coinnect.registration_login.model.User;
import com.coinnect.registration_login.model.UserLogin;
import com.coinnect.registration_login.repository.UserRepository;
import com.coinnect.registration_login.repository.UserLoginRepository;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    private final UserLoginRepository userLoginRepository;  

    private final UserDetailsService userDetailsService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;  

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(RegisterRequestDTO registerRequestDTO) {
        List<User> existingUsersByIdentification = userRepository.findByIdentificationUser(registerRequestDTO.getIdentificationUser());
        if (!existingUsersByIdentification.isEmpty()) {
            throw new ResourceNotFoundException("Usuario ya registrado con esta identificación.");
        }

        if (userRepository.findByUserName(registerRequestDTO.getUserName()).isPresent()) {
            throw new ResourceNotFoundException("Nombre de usuario ya está en uso.");
        }
        if (userRepository.findByEmailUser(registerRequestDTO.getEmailUser()).isPresent()) {
            throw new ResourceNotFoundException("Correo electrónico ya está en uso.");
        }
    
        User newUser = new User();
        newUser.setNameUser(registerRequestDTO.getNameUser());
        newUser.setLastNameUser(registerRequestDTO.getLastNameUser());
        newUser.setIdentificationUser(registerRequestDTO.getIdentificationUser());
        newUser.setEmailUser(registerRequestDTO.getEmailUser());
        newUser.setUserName(registerRequestDTO.getUserName());
    
        String encodedPassword = passwordEncoder.encode(registerRequestDTO.getPassword());
        logger.info("Password encoded for user {}: {}", registerRequestDTO.getUserName(), encodedPassword);
        newUser.setPassword(encodedPassword);
    
        User savedUser = userRepository.save(newUser);
    
        UserLogin userLogin = new UserLogin();
        userLogin.setUser(savedUser);
        userLogin.setUserName(registerRequestDTO.getUserName());
        userLogin.setPassword(encodedPassword);
        userLogin.setRole(Role.USER);
        userLoginRepository.save(userLogin);
    
        return savedUser;
    }
    

    public TokenResponseDTO login(LoginRequestDTO loginRequestDTO) {
        try {
            logger.info("Attempting to authenticate user: {}", loginRequestDTO.getUserName());
    
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequestDTO.getUserName(),
                    loginRequestDTO.getPassword()
                )
            );
            UserLogin userLogin = userLoginRepository.findByUserName(loginRequestDTO.getUserName())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    
            String role = userLogin.getRole().name();
            logger.info("User {} has role: {}", loginRequestDTO.getUserName(), role);
    
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDTO.getUserName());
            String token = tokenService.getToken(userDetails);
    
            return new TokenResponseDTO(token);
    
        } catch (BadCredentialsException e) {
            logger.error("Invalid username or password for user: {}", loginRequestDTO.getUserName(), e);
            throw new BadCredentialsException("Invalid username or password", e);
        } catch (Exception e) {
            logger.error("Login failed for user: {}", loginRequestDTO.getUserName(), e);
            throw new RuntimeException("Login failed", e);
        }
    }
    

}

package com.coinnect.registration_login.user_registration.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coinnect.registration_login.authentication.model.Role;
import com.coinnect.registration_login.authentication.model.UserLogin;
import com.coinnect.registration_login.authentication.repository.UserLoginRepository;
import com.coinnect.registration_login.common.exception.ConflictException;
import com.coinnect.registration_login.user_registration.dto.RegisterRequestDTO;
import com.coinnect.registration_login.user_registration.dto.RegisterResponseDTO;
import com.coinnect.registration_login.user_registration.model.User;
import com.coinnect.registration_login.user_registration.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserLoginRepository userLoginRepository;
    
    public RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO) {

        validateFields(registerRequestDTO);

        validateUserExistence(registerRequestDTO);

        User newUser = createNewUser(registerRequestDTO);

        String encodedPassword = passwordEncoder.encode(registerRequestDTO.getPassword());
        newUser.setPassword(encodedPassword);
        User savedUser = userRepository.save(newUser);  

        saveUserLogin(savedUser, registerRequestDTO, encodedPassword);

        return RegisterResponseDTO.builder()  
            .id(savedUser.getId())
            .nameUser(savedUser.getName())
            .lastNameUser(savedUser.getLastName())
            .identificationUser(savedUser.getIdentification())
            .emailUser(savedUser.getEmail())
            .userName(savedUser.getUserName())
            .createdAt(savedUser.getCreatedAt())  
            .updatedAt(savedUser.getUpdatedAt())  
            .build();
    }

    private void validateFields(RegisterRequestDTO registerRequestDTO) {
        if (registerRequestDTO.getName() == null || registerRequestDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        if (registerRequestDTO.getLastName() == null || registerRequestDTO.getLastName().isEmpty()) {
            throw new IllegalArgumentException("El apellido es obligatorio.");
        }

        if (registerRequestDTO.getIdentification() == null || registerRequestDTO.getIdentification().isEmpty()) {
            throw new IllegalArgumentException("La identificación es obligatoria.");
        }

        if (!registerRequestDTO.getEmail().matches("[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}")) {
            throw new IllegalArgumentException("Correo electrónico inválido.");
        }        

        if (registerRequestDTO.getUserName() == null || registerRequestDTO.getUserName().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio.");
        }

        if (registerRequestDTO.getPassword() == null || registerRequestDTO.getPassword().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }
    }

    private void validateUserExistence(RegisterRequestDTO registerRequestDTO) {

        if (userRepository.findByIdentificationUser(registerRequestDTO.getIdentification()).isPresent()) {
            throw new ConflictException("Usuario ya registrado con esta identificación.");
        }

        if (userRepository.findByUserName(registerRequestDTO.getUserName()).isPresent()) {
            throw new ConflictException("Nombre de usuario ya está en uso.");
        }

        if (userRepository.findByEmailUser(registerRequestDTO.getEmail()).isPresent()) {
            throw new ConflictException("Correo electrónico ya está en uso.");
        }
    }

    private User createNewUser(RegisterRequestDTO registerRequestDTO) {
        User newUser = new User();
        newUser.setName(registerRequestDTO.getName());
        newUser.setLastName(registerRequestDTO.getLastName());
        newUser.setIdentification(registerRequestDTO.getIdentification());
        newUser.setEmail(registerRequestDTO.getEmail());
        newUser.setUserName(registerRequestDTO.getUserName());
        return newUser;  
    }

    private void saveUserLogin(User savedUser, RegisterRequestDTO registerRequestDTO, String encodedPassword) {
        UserLogin userLogin = new UserLogin();
        userLogin.setUser(savedUser);  
        userLogin.setUserName(registerRequestDTO.getUserName());
        userLogin.setPassword(encodedPassword);  
        userLogin.setRole(Role.USER);  
        userLoginRepository.save(userLogin);  
    }

}
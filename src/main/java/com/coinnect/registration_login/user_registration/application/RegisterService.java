package com.coinnect.registration_login.user_registration.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coinnect.registration_login.authentication.domain.Role;
import com.coinnect.registration_login.authentication.domain.UserLogin;
import com.coinnect.registration_login.authentication.infraestructure.UserLoginRepository;
import com.coinnect.registration_login.common.exception.ConflictException;
import com.coinnect.registration_login.user_registration.domain.User;
import com.coinnect.registration_login.user_registration.infraestructure.UserRepository;
import com.coinnect.registration_login.user_registration.presentation.RegisterRequestDTO;
import com.coinnect.registration_login.user_registration.presentation.RegisterResponseDTO;

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
            .nameUser(savedUser.getNameUser())
            .lastNameUser(savedUser.getLastNameUser())
            .identificationUser(savedUser.getIdentificationUser())
            .emailUser(savedUser.getEmailUser())
            .userName(savedUser.getUserName())
            .createdAt(savedUser.getCreatedAt())  
            .updatedAt(savedUser.getUpdatedAt())  
            .build();
    }

    private void validateFields(RegisterRequestDTO registerRequestDTO) {
        if (registerRequestDTO.getNameUser() == null || registerRequestDTO.getNameUser().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        if (registerRequestDTO.getLastNameUser() == null || registerRequestDTO.getLastNameUser().isEmpty()) {
            throw new IllegalArgumentException("El apellido es obligatorio.");
        }

        if (registerRequestDTO.getIdentificationUser() == null || registerRequestDTO.getIdentificationUser().isEmpty()) {
            throw new IllegalArgumentException("La identificación es obligatoria.");
        }

        if (registerRequestDTO.getEmailUser() == null || registerRequestDTO.getEmailUser().isEmpty()) {
            throw new IllegalArgumentException("El correo electrónico es obligatorio.");
        }

        if (registerRequestDTO.getUserName() == null || registerRequestDTO.getUserName().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio.");
        }

        if (registerRequestDTO.getPassword() == null || registerRequestDTO.getPassword().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }
    }

    private void validateUserExistence(RegisterRequestDTO registerRequestDTO) {

        if (userRepository.findByIdentificationUser(registerRequestDTO.getIdentificationUser()).isPresent()) {
            throw new ConflictException("Usuario ya registrado con esta identificación.");
        }

        if (userRepository.findByUserName(registerRequestDTO.getUserName()).isPresent()) {
            throw new ConflictException("Nombre de usuario ya está en uso.");
        }

        if (userRepository.findByEmailUser(registerRequestDTO.getEmailUser()).isPresent()) {
            throw new ConflictException("Correo electrónico ya está en uso.");
        }
    }

    private User createNewUser(RegisterRequestDTO registerRequestDTO) {
        User newUser = new User();
        newUser.setNameUser(registerRequestDTO.getNameUser());
        newUser.setLastNameUser(registerRequestDTO.getLastNameUser());
        newUser.setIdentificationUser(registerRequestDTO.getIdentificationUser());
        newUser.setEmailUser(registerRequestDTO.getEmailUser());
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
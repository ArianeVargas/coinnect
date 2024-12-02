package com.coinnect.registration_login.user_management.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coinnect.registration_login.common.exception.ResourceAlreadyExistsException;
import com.coinnect.registration_login.common.exception.ResourceNotFoundException;
import com.coinnect.registration_login.user_management.dto.EditUserDTO;
import com.coinnect.registration_login.user_registration.model.User;
import com.coinnect.registration_login.user_registration.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManagementService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID " + id));
    }

    public User updateUser(Long id, EditUserDTO editUserDTO){
        User user = getUserById(id);
        
        if (userRepository.existsByEmailUser(editUserDTO.getEmail())) {
            throw new ResourceAlreadyExistsException("El correo electrónico ya está registrado.");
        }
        
        if (userRepository.existsByUserName(editUserDTO.getUserName())) {
            throw new ResourceAlreadyExistsException("El nombre de usuario ya está en uso.");
        }
    
        // Actualización del usuario
        if(editUserDTO.getName() != null){
            user.setName(editUserDTO.getName());
        }
        if(editUserDTO.getLastName() != null){
            user.setLastName(editUserDTO.getLastName());
        }
        if(editUserDTO.getIdentification() != null){
            user.setIdentification(editUserDTO.getIdentification());
        }
        if(editUserDTO.getEmail() != null){
            user.setEmail(editUserDTO.getEmail());
        }
        if(editUserDTO.getUserName() != null){
            user.setUserName(editUserDTO.getUserName());
        }
        if(editUserDTO.getPassword() != null){
            String encodedPassword = passwordEncoder.encode(editUserDTO.getPassword());
            user.setPassword(encodedPassword);
        }
        
        return userRepository.save(user);
    }

    public void deleteUser(Long id){
        if(!userRepository.existsById(id)){
            throw new ResourceNotFoundException("El usuario con ID " + id + " no existe.");
        }
        userRepository.deleteById(id);
    }
    
}

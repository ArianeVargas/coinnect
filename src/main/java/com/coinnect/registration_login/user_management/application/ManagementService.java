package com.coinnect.registration_login.user_management.application;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coinnect.registration_login.common.exception.ResourceNotFoundException;
import com.coinnect.registration_login.user_management.presentation.EditUserDTO;
import com.coinnect.registration_login.user_registration.domain.User;
import com.coinnect.registration_login.user_registration.infraestructure.UserRepository;

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
        if(editUserDTO.getNameUser() != null){
            user.setNameUser(editUserDTO.getNameUser());
        }
        if(editUserDTO.getLastNameUser() != null){
            user.setLastNameUser(editUserDTO.getLastNameUser());
        }
        if(editUserDTO.getIdentificationUser() != null){
            user.setIdentificationUser(editUserDTO.getIdentificationUser());
        }
        if(editUserDTO.getEmailUser() != null){
            user.setEmailUser(editUserDTO.getEmailUser());
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

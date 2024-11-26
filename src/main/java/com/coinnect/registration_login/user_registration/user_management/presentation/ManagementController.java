package com.coinnect.registration_login.user_registration.user_management.presentation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coinnect.registration_login.user_registration.domain.User;
import com.coinnect.registration_login.user_registration.user_management.application.ManagementService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/management")
public class ManagementController {

    @Autowired
    private ManagementService managementService;

    @GetMapping
    public List<User> users(){
        return managementService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = managementService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @Valid @RequestBody EditUserDTO editUserDTO) {
        User updateUser = managementService.updateUser(id, editUserDTO);       
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        managementService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

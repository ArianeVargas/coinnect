package com.coinnect.registration_login.user_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coinnect.registration_login.user_management.dto.EditUserDTO;
import com.coinnect.registration_login.user_management.service.ManagementService;
import com.coinnect.registration_login.user_registration.model.User;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

     @Autowired
    private ManagementService managementService;

    @PreAuthorize("#id == principal.id or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = managementService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);  
    }

    @PreAuthorize("#id == principal.id or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @Valid @RequestBody EditUserDTO editUserDTO) {
        User updateUser = managementService.updateUser(id, editUserDTO);       
        return new ResponseEntity<>(updateUser, HttpStatus.OK);  
    }

    @PreAuthorize("#id == principal.id or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        managementService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);  
    }
}
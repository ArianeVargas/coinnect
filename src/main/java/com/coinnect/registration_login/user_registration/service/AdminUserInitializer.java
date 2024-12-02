package com.coinnect.registration_login.user_registration.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.coinnect.registration_login.authentication.model.Role;
import com.coinnect.registration_login.authentication.model.UserLogin;
import com.coinnect.registration_login.authentication.repository.UserLoginRepository;
import com.coinnect.registration_login.user_registration.model.User;
import com.coinnect.registration_login.user_registration.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminUserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserLoginRepository userLoginRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUserName("admin").isEmpty()) {

            User adminUser = new User();
            adminUser.setName("Admin");
            adminUser.setLastName("User");
            adminUser.setIdentification("123456789");
            adminUser.setEmail("admin@example.com");
            adminUser.setUserName("admin");

            String encodedPassword = passwordEncoder.encode("admin123");  
            adminUser.setPassword(encodedPassword);
            
            User savedUser = userRepository.save(adminUser);

            UserLogin adminUserLogin = new UserLogin();
            adminUserLogin.setUser(savedUser);
            adminUserLogin.setUserName("admin");
            adminUserLogin.setPassword(encodedPassword);
            adminUserLogin.setRole(Role.ADMIN);  
            userLoginRepository.save(adminUserLogin);

            System.out.println("Usuario ADMIN creado correctamente.");
        }
    }
}

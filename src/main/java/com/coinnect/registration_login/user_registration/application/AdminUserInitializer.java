package com.coinnect.registration_login.user_registration.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.coinnect.registration_login.authentication.domain.Role;
import com.coinnect.registration_login.authentication.domain.UserLogin;
import com.coinnect.registration_login.authentication.infraestructure.UserLoginRepository;
import com.coinnect.registration_login.user_registration.domain.User;
import com.coinnect.registration_login.user_registration.infraestructure.UserRepository;

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
            adminUser.setNameUser("Admin");
            adminUser.setLastNameUser("User");
            adminUser.setIdentificationUser("123456789");
            adminUser.setEmailUser("admin@example.com");
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

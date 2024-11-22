package com.coinnect.registration_login.user_registration.infraestructure;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coinnect.registration_login.user_registration.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUserName(String userName);

    Optional<User> findByEmailUser(String emailUser);

    Optional<User> findByIdentificationUser(String identificationUser);
}

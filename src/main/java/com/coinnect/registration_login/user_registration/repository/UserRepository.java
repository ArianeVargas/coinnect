package com.coinnect.registration_login.user_registration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coinnect.registration_login.user_registration.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUserName(String userName);

    Optional<User> findByEmailUser(String emailUser);

    Optional<User> findByIdentificationUser(String identificationUser);

    boolean existsByEmailUser(String emailUser);

    boolean existsByUserName(String userName);
}

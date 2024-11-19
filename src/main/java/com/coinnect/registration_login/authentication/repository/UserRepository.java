package com.coinnect.registration_login.authentication.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coinnect.registration_login.authentication.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUserName(String userName);

    Optional<User> findByEmailUser(String emailUser);

    Optional<User> findByIdentificationUser(String identificationUser);
}

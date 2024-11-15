package com.coinnect.registration_login.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coinnect.registration_login.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

    List<User> findByIdentificationUser(String identificationUser);
    Optional<User> findByUserName(String userName);
}

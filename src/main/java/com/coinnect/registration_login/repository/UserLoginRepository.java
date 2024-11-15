package com.coinnect.registration_login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coinnect.registration_login.model.UserLogin;

public interface UserLoginRepository extends JpaRepository<UserLogin, Long>{

    Optional<UserLogin> findByUserName(String userName);
}

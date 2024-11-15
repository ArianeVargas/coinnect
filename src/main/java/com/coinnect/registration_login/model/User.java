package com.coinnect.registration_login.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name_user")
    private String nameUser;

    @Column(name="last_name_user")
    private String lastNameUser;

    @Column(name="identification_user")
    private String identificationUser;

    @Column(name="email_user")
    private String emailUser;

    @Column(name="user_name")
    private String userName;

    private String password;

}

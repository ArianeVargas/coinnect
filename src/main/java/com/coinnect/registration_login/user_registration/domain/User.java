package com.coinnect.registration_login.user_registration.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.coinnect.registration_login.authentication.domain.UserLogin;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name_user")
    private String nameUser;

    @NotNull
    @Column(name = "last_name_user")
    private String lastNameUser;

    @NotNull
    @Column(name = "identification_user", unique = true)
    private String identificationUser;

    @NotNull
    @Email
    @Column(name = "email_user", unique = true)
    private String emailUser;

    @NotNull
    @Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters")
    @Column(name = "user_name", unique = true)
    private String userName;

    @NotNull
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserLogin userLogin;

}

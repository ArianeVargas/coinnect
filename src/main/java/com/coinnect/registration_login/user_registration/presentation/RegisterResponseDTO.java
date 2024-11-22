package com.coinnect.registration_login.user_registration.presentation;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponseDTO {

    private Long id;
    private String nameUser;
    private String lastNameUser;
    private String identificationUser;
    private String emailUser;
    private String userName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

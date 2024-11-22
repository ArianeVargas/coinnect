package com.coinnect.registration_login.user_registration.user_management.presentation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditUserDTO {

    @NotNull
    private String nameUser;

    @NotNull
    private String lastNameUser;

    @NotNull
    private String identificationUser;

    @NotNull
    @Email
    private String emailUser;

    @NotNull
    @Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters")
    private String userName;

    @NotNull
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

}

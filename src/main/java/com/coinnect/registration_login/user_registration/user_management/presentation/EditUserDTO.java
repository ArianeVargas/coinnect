package com.coinnect.registration_login.user_registration.user_management.presentation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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
    private String userName;

    @NotNull
    private String password;

}

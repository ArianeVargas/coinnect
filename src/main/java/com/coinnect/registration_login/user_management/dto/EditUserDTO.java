package com.coinnect.registration_login.user_management.dto;

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
    private String name;

    @NotNull
    private String lastName;

    @NotNull
    private String identification;

    @NotNull
    @Email(message = "El correo electrónico debe ser válido.")
    private String email;

    @NotNull
    private String userName;

    @NotNull
    private String password;

}

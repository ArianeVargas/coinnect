package com.coinnect.registration_login.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nameUser;

    @NotBlank(message = "El apellido no puede estar vacío")
    private String lastNameUser;

    @NotBlank(message = "La identificación no puede estar vacía")
    @Size(min = 6, max = 12, message = "La identificación debe tener entre 6 y 12 caracteres")
    private String identificationUser;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Debe proporcionar un email válido")
    private String emailUser;

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    private String userName;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

}

package com.coinnect.registration_login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {

    private String nameUser;
    private String lastNameUser;
    private String identificationUser;
    private String emailUser;
    private String userName;
    private String password;

}

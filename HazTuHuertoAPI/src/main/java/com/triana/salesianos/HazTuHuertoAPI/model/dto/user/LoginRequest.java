package com.triana.salesianos.HazTuHuertoAPI.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotEmpty(message = "{login.username.notempty}")
    private String username;
    @NotEmpty(message = "{login.password.notempty}")
    private String password;

}

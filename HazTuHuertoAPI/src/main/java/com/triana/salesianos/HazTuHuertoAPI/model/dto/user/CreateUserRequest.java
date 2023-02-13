package com.triana.salesianos.HazTuHuertoAPI.model.dto.user;

import com.triana.salesianos.HazTuHuertoAPI.validation.annotation.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest {
    @NotEmpty(message = "{createUserRequest.username.notempty}")
    @UniqueUsername(message = "{createUserRequest.username.unique}")
    private String username;
    private String password;
    private String verifyPassword;
    private String avatar;
    private String fullName;

}

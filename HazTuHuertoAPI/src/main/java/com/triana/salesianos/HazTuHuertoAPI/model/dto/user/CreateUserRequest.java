package com.triana.salesianos.HazTuHuertoAPI.model.dto.user;

import com.triana.salesianos.HazTuHuertoAPI.validation.annotation.FieldsValueMatch;
import com.triana.salesianos.HazTuHuertoAPI.validation.annotation.StrongPassword;
import com.triana.salesianos.HazTuHuertoAPI.validation.annotation.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password", fieldMatch = "verifyPassword",
                message = "{createUserRequest.password.nomatch}"
        ),
        @FieldsValueMatch(
                field = "email", fieldMatch = "verifyEmail",
                message = "{createUserRequest.emails.nomatch}"
        )
})
public class CreateUserRequest {
    @NotEmpty(message = "{createUserRequest.username.notempty}")
    @UniqueUsername(message = "{createUserRequest.username.unique}")
    private String username;
    @StrongPassword
    @NotEmpty(message = "{createUserRequest.password.notempty}")
    private String password;
    @NotEmpty(message = "{createUserRequest.password.notempty}")
    private String verifyPassword;
    @URL(message = "{createUserRequest.avatar.url}")
    private String avatar;
    @NotEmpty(message = "{createUserRequest.fullname.notempty}")
    private String fullName;
   // @UniqueEmail(message = "{createUserRequest.email.unique}")
    @Email
    @NotEmpty(message = "{createUserRequest.email.notempty}")
    private String email;
    @NotEmpty(message = "{createUserRequest.email.notempty}")
    private String verifyEmail;
}

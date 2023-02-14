package com.triana.salesianos.HazTuHuertoAPI.model.dto.user;

import com.triana.salesianos.HazTuHuertoAPI.validation.annotation.FieldsValueMatch;
import com.triana.salesianos.HazTuHuertoAPI.validation.annotation.StrongPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
@FieldsValueMatch.List({
                @FieldsValueMatch(
                field = "newPassword", fieldMatch = "newVerifyPassword",
                message = "{changePasswordRequest.password.nomatch}"
        )
})
public class ChangePasswordRequest {

    @NotEmpty(message = "{changePasswordRequest.password.notempty}")
    private String oldPassword;
    @StrongPassword
    @NotEmpty(message = "{changePasswordRequest.password.notempty}")
    private String newPassword;
    @NotEmpty(message = "{changePasswordRequest.password.notempty}")
    private String newVerifyPassword;

}

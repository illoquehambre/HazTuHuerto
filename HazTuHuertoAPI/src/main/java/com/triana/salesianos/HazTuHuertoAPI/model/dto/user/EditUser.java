package com.triana.salesianos.HazTuHuertoAPI.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditUser {

    @NotEmpty(message = "{userDto.fullname.notempty}")
    private String fullName;

    @URL(message = "{userDto.avatar.url}")
    private String avatar;
}

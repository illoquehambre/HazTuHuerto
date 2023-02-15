package com.triana.salesianos.HazTuHuertoAPI.model.dto.answer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAnswer {

    @Size(max = 1000, min = 100)
    @NotEmpty(message = "{createUserRequest.password.notempty}")
    private String content;
    @URL
    private String urlImg;
}

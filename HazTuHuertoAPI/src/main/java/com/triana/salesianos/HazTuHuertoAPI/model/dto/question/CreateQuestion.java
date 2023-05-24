package com.triana.salesianos.HazTuHuertoAPI.model.dto.question;

import com.triana.salesianos.HazTuHuertoAPI.validation.annotation.StrongPassword;
import com.triana.salesianos.HazTuHuertoAPI.validation.annotation.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateQuestion {

    @NotEmpty(message = "{createQuestion.title.notempty}")
    @Size(max = 50)
    private String title;
    @Size(max = 1000, min = 100)
    @NotEmpty(message = "{createQuestion.content.notempty}")
    private String content;
    @URL
    private String urlImg;

}

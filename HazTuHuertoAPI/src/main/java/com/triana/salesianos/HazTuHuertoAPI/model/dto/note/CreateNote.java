package com.triana.salesianos.HazTuHuertoAPI.model.dto.note;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNote {
    @NotEmpty(message = "{createQuestion.title.notempty}")
    private String title;
    private String content;

}

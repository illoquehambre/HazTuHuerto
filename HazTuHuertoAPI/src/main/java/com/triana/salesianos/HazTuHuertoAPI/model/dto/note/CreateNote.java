package com.triana.salesianos.HazTuHuertoAPI.model.dto.note;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNote {
    @NotEmpty(message = "{createNote.title.notempty}")
    @Size(max = 50)
    private String title;
    @NotEmpty(message = "{createNote.content.notempty}")
    private String content;

}

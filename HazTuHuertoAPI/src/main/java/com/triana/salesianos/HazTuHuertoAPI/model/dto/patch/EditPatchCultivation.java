package com.triana.salesianos.HazTuHuertoAPI.model.dto.patch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditPatchCultivation {
    @NotEmpty(message = "{createQuestion.title.notempty}")
    private String patchName;
    @NotEmpty(message = "{createQuestion.title.notempty}")
    private String cultivationName;
    private String variety;
    private LocalDate plantDate;
    private LocalDate harvestDate;
    @URL
    private String cultivationImg;
}

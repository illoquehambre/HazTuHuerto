package com.triana.salesianos.HazTuHuertoAPI.model.dto.patch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePatchCultivation {
    @NotEmpty(message = "{createQuestion.title.notempty}")
    private String patchName;
    @NotEmpty(message = "{createQuestion.title.notempty}")
    private String cultivationName;
    private String variety;
    //Seria planteable el hecho de que estos datos no se pidan al crear el cultivo y que se añadan a posteriori
    private LocalDate plantDate;//Este atributo deberá setearse a la fecha actual en caso de que venga nulo
    private LocalDate harvestDate;
}

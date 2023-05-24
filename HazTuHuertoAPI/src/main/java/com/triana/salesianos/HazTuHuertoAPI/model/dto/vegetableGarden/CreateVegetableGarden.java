package com.triana.salesianos.HazTuHuertoAPI.model.dto.vegetableGarden;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateVegetableGarden {

    @NotEmpty(message = "{createQuestion.title.notempty}")
    private String name;
    private String latitude;
    private String longitude;
    @URL
    private String urlImg;
}

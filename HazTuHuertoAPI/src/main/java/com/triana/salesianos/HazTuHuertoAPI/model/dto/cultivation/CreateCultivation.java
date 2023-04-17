package com.triana.salesianos.HazTuHuertoAPI.model.dto.cultivation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCultivation {
    @NotEmpty(message = "{createQuestion.title.notempty}")
    private String name;
    private String variety;
    private LocalDate plantDate;
    private LocalDate harvestDate;
    @URL
    private String cultivationImg;
}

package com.triana.salesianos.HazTuHuertoAPI.model.dto.cultivation;

import com.triana.salesianos.HazTuHuertoAPI.model.Cultivation;
import com.triana.salesianos.HazTuHuertoAPI.model.Patch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CultivationResponse {


    private String name;
    private LocalDate plantDate;
    private LocalDate harvestDate;
    private long daysLeft;
    private long daysPlanted;
    private String cultivationImg;
    private int numNotes;

    public static CultivationResponse fromCultivation(Cultivation cultivation) {

        return CultivationResponse.builder()
                .name(cultivation.getName())
                .plantDate(cultivation.getPlantingDate())
                .harvestDate(cultivation.getHarvestingDate())
                .daysLeft(ChronoUnit.DAYS.between(cultivation.getHarvestingDate(),
                        cultivation.getPlantingDate()))
                .daysPlanted(ChronoUnit.DAYS.between(cultivation.getPlantingDate(),
                        cultivation.getHarvestingDate()))
                .cultivationImg(cultivation.getImg())
                .numNotes(cultivation.getNoteList().size())
                .build();
    }
}

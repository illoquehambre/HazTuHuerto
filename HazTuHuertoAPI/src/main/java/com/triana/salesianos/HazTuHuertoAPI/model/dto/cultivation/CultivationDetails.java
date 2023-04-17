package com.triana.salesianos.HazTuHuertoAPI.model.dto.cultivation;

import com.triana.salesianos.HazTuHuertoAPI.model.Cultivation;
import com.triana.salesianos.HazTuHuertoAPI.model.Note;
import com.triana.salesianos.HazTuHuertoAPI.model.Patch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CultivationDetails {

    private String name;
    private String variety;
    private LocalDate plantDate;
    private LocalDate harvestDate;
    private long daysLeft;
    private long daysPlanted;
    private String cultivationImg;
    private List<String> notesTitles;

    public static CultivationDetails fromCultivation(Cultivation cultivation) {

        return CultivationDetails.builder()
                .name(cultivation.getName())
                .variety(cultivation.getVariety())
                .plantDate(cultivation.getPlantingDate())
                .harvestDate(cultivation.getHarvestingDate())
                .daysLeft(ChronoUnit.DAYS.between(cultivation.getHarvestingDate(),
                        cultivation.getPlantingDate()))
                .daysPlanted(ChronoUnit.DAYS.between(cultivation.getPlantingDate(),
                        cultivation.getHarvestingDate()))
                .cultivationImg(cultivation.getImg())
                .notesTitles(cultivation.getNoteList().stream().map(Note::getTitle).toList())
                .build();
    }
}

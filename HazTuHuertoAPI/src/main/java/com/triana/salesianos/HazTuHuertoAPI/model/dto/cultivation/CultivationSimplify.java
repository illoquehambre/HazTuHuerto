package com.triana.salesianos.HazTuHuertoAPI.model.dto.cultivation;

import com.triana.salesianos.HazTuHuertoAPI.model.Cultivation;
import com.triana.salesianos.HazTuHuertoAPI.model.Patch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CultivationSimplify {

    private String cultivationName;
    private long daysLeft;



    public static CultivationSimplify fromCultivation(Cultivation cultivation) {

        return CultivationSimplify.builder()
                .cultivationName(cultivation.getName())
                .daysLeft(ChronoUnit.DAYS.between(cultivation.getHarvestingDate(),
                        cultivation.getPlantingDate()))
                .build();
    }
}

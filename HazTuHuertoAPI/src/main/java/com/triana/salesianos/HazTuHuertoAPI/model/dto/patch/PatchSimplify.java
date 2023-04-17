package com.triana.salesianos.HazTuHuertoAPI.model.dto.patch;

import com.triana.salesianos.HazTuHuertoAPI.model.Patch;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.cultivation.CultivationSimplify;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatchSimplify {

    private CultivationSimplify cultivation;
    //Lo mismo esta clase no tiene mucho sentido y se deberia obviar


    public static PatchSimplify fromPatch(Patch patch) {

        return PatchSimplify.builder()
                .cultivation(CultivationSimplify.fromCultivation(patch.getCultivation()))
                .build();
    }
}

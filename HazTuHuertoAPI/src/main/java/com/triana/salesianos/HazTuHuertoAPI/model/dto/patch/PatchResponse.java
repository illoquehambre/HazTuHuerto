package com.triana.salesianos.HazTuHuertoAPI.model.dto.patch;

import com.triana.salesianos.HazTuHuertoAPI.model.Patch;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.cultivation.CultivationResponse;
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
public class PatchResponse {

    private String name;
    private CultivationResponse cultivation;

    public static PatchResponse fromPatch(Patch patch) {

        return PatchResponse.builder()
                .name(patch.getName())
                .cultivation(CultivationResponse.fromCultivation(patch.getCultivation()))
                .build();
    }
}

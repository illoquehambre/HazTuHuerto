package com.triana.salesianos.HazTuHuertoAPI.model.dto.patch;

import com.triana.salesianos.HazTuHuertoAPI.model.Note;
import com.triana.salesianos.HazTuHuertoAPI.model.Patch;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.cultivation.CultivationDetails;
import com.triana.salesianos.HazTuHuertoAPI.validation.annotation.FieldsValueMatch;
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
public class PatchDetails {

    private String name;
    private CultivationDetails cultivation;

    public static PatchDetails fromPatch(Patch patch) {

        return PatchDetails.builder()
                .name(patch.getName())
                .cultivation(CultivationDetails.fromCultivation(patch.getCultivation()))
                .build();
    }
}

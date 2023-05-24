package com.triana.salesianos.HazTuHuertoAPI.model.dto.patch;

import com.triana.salesianos.HazTuHuertoAPI.model.Patch;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.cultivation.CultivationDetails;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.cultivation.CultivationResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatchHistory {

    private String name;
    private Long id;
    private List<CultivationResponse> cultivationHistory;

    public static PatchHistory fromPatch(Patch patch) {

        return PatchHistory.builder()
                .name(patch.getName())
                .id(patch.getId())
                .cultivationHistory(patch.getCultivationHistory().stream().map(
                        CultivationResponse::fromCultivation).toList())
                .build();
    }
}

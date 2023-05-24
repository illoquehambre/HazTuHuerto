package com.triana.salesianos.HazTuHuertoAPI.model.dto.vegetableGarden;

import com.triana.salesianos.HazTuHuertoAPI.model.VegetableGarden;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.patch.PatchResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VegetableGardenDetails {


    private String id;
    private String name;
    private String latitude, longitude;
    private List<PatchResponse> patchList; //estod eberá ser un dto
    private String img;

    public static VegetableGardenDetails fromGarden(VegetableGarden garden) {

        return VegetableGardenDetails.builder()
                .id(garden.getId().toString())
                .name(garden.getName())
                .latitude(garden.getLatitude())
                .longitude(garden.getLongitude())
                .patchList(garden.getPatchList().stream().map(PatchResponse::fromPatch).toList())
                .img(garden.getImg())
                .build();
    }
}

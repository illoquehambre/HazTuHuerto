package com.triana.salesianos.HazTuHuertoAPI.model.dto.vegetableGarden;

import com.triana.salesianos.HazTuHuertoAPI.model.VegetableGarden;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.patch.PatchSimplify;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VegetableGardenResponse {

    private String id;
    private String name;
    //Localizacion???
    private String latitude, longitude;
    private String img;
    private int numPatch;
    private List<PatchSimplify> patchList;

    public static VegetableGardenResponse fromGarden(VegetableGarden garden) {

        return VegetableGardenResponse.builder()
                .id(garden.getId().toString())
                .name(garden.getName())
                .latitude(garden.getLatitude())
                .longitude(garden.getLongitude())
                .img(garden.getImg())
                .numPatch(garden.getPatchList().size())
                .patchList(garden.getPatchList().stream().map(PatchSimplify::fromPatch).toList())
                .build();
    }
}

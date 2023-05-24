package com.triana.salesianos.HazTuHuertoAPI.search.spec;

import com.triana.salesianos.HazTuHuertoAPI.model.Question;
import com.triana.salesianos.HazTuHuertoAPI.model.VegetableGarden;
import com.triana.salesianos.HazTuHuertoAPI.search.util.SearchCriteria;

import java.util.List;

public class GardenSpecificationBuilder extends GenericSpecificationBuilder<VegetableGarden> {
    public GardenSpecificationBuilder(List<SearchCriteria> params) {super(params, VegetableGarden.class);
    }

}

package com.triana.salesianos.HazTuHuertoAPI.search.spec;

import com.triana.salesianos.HazTuHuertoAPI.model.User;
import com.triana.salesianos.HazTuHuertoAPI.search.util.SearchCriteria;

import java.util.List;

public class UserSpecificationBuilder extends GenericSpecificationBuilder<User> {
    public UserSpecificationBuilder(List<SearchCriteria> params) {
        super(params, User.class);
    }

}

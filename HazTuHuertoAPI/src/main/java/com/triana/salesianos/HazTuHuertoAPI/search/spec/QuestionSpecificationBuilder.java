package com.triana.salesianos.HazTuHuertoAPI.search.spec;

import com.triana.salesianos.HazTuHuertoAPI.model.Question;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import com.triana.salesianos.HazTuHuertoAPI.search.util.SearchCriteria;

import java.util.List;

public class QuestionSpecificationBuilder extends GenericSpecificationBuilder<Question> {
    public QuestionSpecificationBuilder(List<SearchCriteria> params) {
        super(params, Question.class);
    }

}

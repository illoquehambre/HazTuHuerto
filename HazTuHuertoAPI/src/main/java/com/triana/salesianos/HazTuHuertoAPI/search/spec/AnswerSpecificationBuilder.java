package com.triana.salesianos.HazTuHuertoAPI.search.spec;

import com.triana.salesianos.HazTuHuertoAPI.model.Answer;
import com.triana.salesianos.HazTuHuertoAPI.model.Question;
import com.triana.salesianos.HazTuHuertoAPI.search.util.SearchCriteria;

import java.util.List;

public class AnswerSpecificationBuilder extends GenericSpecificationBuilder<Answer> {
    public AnswerSpecificationBuilder(List<SearchCriteria> params) {
        super(params, Answer.class);
    }

}

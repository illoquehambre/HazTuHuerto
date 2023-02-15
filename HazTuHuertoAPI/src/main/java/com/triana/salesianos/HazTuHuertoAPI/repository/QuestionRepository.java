package com.triana.salesianos.HazTuHuertoAPI.repository;

import com.triana.salesianos.HazTuHuertoAPI.model.Question;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository <Question, Long> {

    List<Question> findAllByPublisherUsername(String publisher);
}

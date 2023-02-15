package com.triana.salesianos.HazTuHuertoAPI.repository;

import com.triana.salesianos.HazTuHuertoAPI.model.Answer;
import com.triana.salesianos.HazTuHuertoAPI.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository <Answer, Long> {
    List<Answer> findAllByQuestionId(Long id);
}

package com.triana.salesianos.HazTuHuertoAPI.repository;

import com.triana.salesianos.HazTuHuertoAPI.model.Answer;
import com.triana.salesianos.HazTuHuertoAPI.model.Question;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface AnswerRepository extends JpaRepository <Answer, Long>,
        JpaSpecificationExecutor<Answer> {
    List<Answer> findAllByQuestionId(Long id);
    List<Answer> findAllByPublisherId(UUID id);
}

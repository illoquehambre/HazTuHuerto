package com.triana.salesianos.HazTuHuertoAPI.service;

import com.triana.salesianos.HazTuHuertoAPI.model.Question;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import com.triana.salesianos.HazTuHuertoAPI.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    public List<Question> findAll() {

        List<Question> result = questionRepository.findAll();

        if (result.isEmpty())
            throw new EntityNotFoundException("No questions with this search criteria");

        return questionRepository.findAll();
    }

    public List<Question> findByUserName(String publisher) {

        List<Question> result = questionRepository.findAllByPublisher(publisher);

        if (result.isEmpty())
            throw new EntityNotFoundException("No questions with this search criteria");

        return result;
    }

    public Question findById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No user with id: " + id));

    }
}

package com.triana.salesianos.HazTuHuertoAPI.service;

import com.triana.salesianos.HazTuHuertoAPI.model.Answer;
import com.triana.salesianos.HazTuHuertoAPI.model.Question;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.question.CreateQuestion;
import com.triana.salesianos.HazTuHuertoAPI.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
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

        List<Question> result = questionRepository.findAllByPublisherUsername(publisher);

        if (result.isEmpty())
            throw new EntityNotFoundException("No questions with this search criteria");

        return result;
    }

    public Question findById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No user with id: " + id));

    }

    public Question save(CreateQuestion newQuest, User user) {

        return questionRepository.save(
                Question.builder()
                        .title(newQuest.getTitle())
                        .content(newQuest.getContent())
                        .urlImg(newQuest.getUrlImg())
                        .createdAt(LocalDateTime.now())
                        .publisher(user)
                        .build());


    }

    public void deleteById(Long id) {
        // Prevenimos errores al intentar borrar algo que no existe
        if (questionRepository.existsById(id))
            questionRepository.deleteById(id);
    }

    public void addAnswer(Question question, Answer answer){
        question.getAnswers().add(answer);
        questionRepository.save(question);
    }
}

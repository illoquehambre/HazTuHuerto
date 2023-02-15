package com.triana.salesianos.HazTuHuertoAPI.service;

import com.triana.salesianos.HazTuHuertoAPI.model.Answer;
import com.triana.salesianos.HazTuHuertoAPI.model.Question;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.answer.CreateAnswer;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.question.CreateQuestion;
import com.triana.salesianos.HazTuHuertoAPI.repository.AnswerRepository;
import com.triana.salesianos.HazTuHuertoAPI.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    public List<Answer> findAll() {

        List<Answer> result = answerRepository.findAll();

        if (result.isEmpty())
            throw new EntityNotFoundException("No questions with this search criteria");

        return answerRepository.findAll();
    }

    public List<Answer> findAllByQuestion(Long id) {

        List<Answer> result = answerRepository.findAllByQuestionId(id);

        if (result.isEmpty())
            throw new EntityNotFoundException("No questions with this search criteria");

        return answerRepository.findAll();
    }
    public List<Answer> findAllByUserName(String name) {

        List<Answer> result = answerRepository.findAllByPublisherUsername(name);

        if (result.isEmpty())
            throw new EntityNotFoundException("No questions with this search criteria");

        return answerRepository.findAll();
    }


    public Answer findById(Long id) {
        return answerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No user with id: " + id));

    }

    public Answer save(CreateAnswer newAnswer, User user, Question question) {

        return answerRepository.save(
                Answer.builder()
                        .content(newAnswer.getContent())
                        .urlImg(newAnswer.getUrlImg())
                        .publisher(user)
                        .question(question)
                        .build());

    }



    public void deleteById(Long id) {
        // Prevenimos errores al intentar borrar algo que no existe
        if (answerRepository.existsById(id))
            answerRepository.deleteById(id);
    }
}

package com.triana.salesianos.HazTuHuertoAPI.service;

import com.triana.salesianos.HazTuHuertoAPI.model.Answer;
import com.triana.salesianos.HazTuHuertoAPI.model.Question;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.PageDto;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.answer.AnswerResponse;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.answer.CreateAnswer;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.question.CreateQuestion;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.question.QuestionResponse;
import com.triana.salesianos.HazTuHuertoAPI.repository.AnswerRepository;
import com.triana.salesianos.HazTuHuertoAPI.repository.QuestionRepository;
import com.triana.salesianos.HazTuHuertoAPI.search.spec.AnswerSpecificationBuilder;
import com.triana.salesianos.HazTuHuertoAPI.search.spec.QuestionSpecificationBuilder;
import com.triana.salesianos.HazTuHuertoAPI.search.util.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final UserService userService;
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
        User publisher= userService.findByUsername(name);
        List<Answer> result = answerRepository.findAllByPublisherId(publisher.getId());

        if (result.isEmpty())
            throw new EntityNotFoundException("No questions with this search criteria");

        return result;
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

    public PageDto<AnswerResponse> search(List<SearchCriteria> params, Pageable pageable) {
        AnswerSpecificationBuilder answerSpecificationBuilder =
                new AnswerSpecificationBuilder(params);
        Specification<Answer> spec =  answerSpecificationBuilder.build();

        Page<AnswerResponse> pageAnswerResponse = answerRepository.findAll(spec, pageable).map(AnswerResponse::fromAnswer);
        if(pageAnswerResponse.isEmpty())
            throw new EntityNotFoundException("No answers with this search criteria");
        return new PageDto<>(pageAnswerResponse);
    }

    public void deleteById(Long id) {
        //Esto es una gitanada, perdón
        if (answerRepository.existsById(id)){
            Answer answer =  this.findById(id);
            answer.getQuestion().getAnswers().remove(answer);
            answerRepository.deleteById(id);
        }

    }
}

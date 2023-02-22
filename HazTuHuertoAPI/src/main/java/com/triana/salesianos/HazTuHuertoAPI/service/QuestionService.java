package com.triana.salesianos.HazTuHuertoAPI.service;

import com.triana.salesianos.HazTuHuertoAPI.files.service.StorageService;
import com.triana.salesianos.HazTuHuertoAPI.model.Answer;
import com.triana.salesianos.HazTuHuertoAPI.model.Question;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.PageDto;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.question.CreateQuestion;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.question.QuestionDetails;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.question.QuestionResponse;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.user.UserResponse;
import com.triana.salesianos.HazTuHuertoAPI.repository.QuestionRepository;
import com.triana.salesianos.HazTuHuertoAPI.repository.UserRepository;
import com.triana.salesianos.HazTuHuertoAPI.search.spec.QuestionSpecificationBuilder;
import com.triana.salesianos.HazTuHuertoAPI.search.spec.UserSpecificationBuilder;
import com.triana.salesianos.HazTuHuertoAPI.search.util.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional

public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final StorageService storageService;

    @Autowired
    private EntityManager entityManager;
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
    public PageDto<QuestionResponse> search(List<SearchCriteria> params, Pageable pageable) {
        QuestionSpecificationBuilder questionSpecificationBuilder =
                new QuestionSpecificationBuilder(params);
        Specification<Question> spec =  questionSpecificationBuilder.build(); //Spec queda nulo, porque?

        Page<QuestionResponse> pageQuestionResponse = questionRepository.findAll(spec, pageable).map(QuestionResponse::fromQuestion);
        if(pageQuestionResponse.isEmpty())
            throw new EntityNotFoundException("No questions with this search criteria");
        return new PageDto<>(pageQuestionResponse);
    }

    public Question save(CreateQuestion newQuest, User user, MultipartFile file) {
        String fileName = storageService.store(file);

        return questionRepository.save(
                Question.builder()
                        .title(newQuest.getTitle())
                        .content(newQuest.getContent())
                        .urlImg(fileName)
                        .createdAt(LocalDateTime.now())
                        .publisher(user)
                        .build());


    }

    public void deleteById(Long id) {
        // Prevenimos errores al intentar borrar algo que no existe
        if (questionRepository.existsById(id))
            questionRepository.deleteById(id);

    }



    public Question likeQuestion(User user, Question question){

        List<User> lista = new ArrayList<>(question.getLikes().stream().toList());

            if(userRepository.checkUserLiked(user.getId(),question.getId())){

                lista.remove(lista.indexOf(user)+1);
                question.setLikes((new HashSet<>(lista)));
                user.setReputation(user.getReputation()-1);
                userRepository.save(user);

            }else{
                question.getLikes().add(user);
                user.setReputation(user.getReputation()+1);
                userRepository.save(user);
            }
       return  questionRepository.save(question);
    }
}

package com.triana.salesianos.HazTuHuertoAPI.service;

import com.triana.salesianos.HazTuHuertoAPI.model.Answer;
import com.triana.salesianos.HazTuHuertoAPI.model.Question;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.question.CreateQuestion;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.question.QuestionDetails;
import com.triana.salesianos.HazTuHuertoAPI.repository.QuestionRepository;
import com.triana.salesianos.HazTuHuertoAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void addAnswer(Question question, Answer answer, User user){
        question.getAnswers().add(answer);

    }

    public Question likeQuestion(User user, Question question){
        //comprobar si esta ya añadido a la lista//Si: quitar
        //            //No: Añadir
        //        //Guardar
        List<User> lista = new ArrayList<>(question.getLikes().stream().toList());
            if(userRepository.checkUserLiked(user.getId(),question.getId())){

                lista.remove(lista.indexOf(user)+1);
                question.setLikes((new HashSet<>(lista)));

            }else{
                question.getLikes().add(user);
            }
       return  questionRepository.save(question);
    }
}

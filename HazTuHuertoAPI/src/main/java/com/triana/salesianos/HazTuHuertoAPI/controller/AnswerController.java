package com.triana.salesianos.HazTuHuertoAPI.controller;

import com.triana.salesianos.HazTuHuertoAPI.model.Answer;
import com.triana.salesianos.HazTuHuertoAPI.model.Question;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.answer.AnswerResponse;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.answer.CreateAnswer;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.question.CreateQuestion;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.question.QuestionDetails;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.question.QuestionResponse;
import com.triana.salesianos.HazTuHuertoAPI.service.AnswerService;
import com.triana.salesianos.HazTuHuertoAPI.service.QuestionService;
import com.triana.salesianos.HazTuHuertoAPI.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
public class AnswerController {




    private final QuestionService questionService;
    private final UserService userService;
    private final AnswerService answerService;

    //VerTodasLasREspuestasDEUnUsuario(GET)

    @GetMapping("/answer/user/{name}")
    public List<AnswerResponse> findByuserName(@PathVariable String name) {

        return answerService.findAllByUserName(name)
                .stream()
                .map(AnswerResponse::fromAnswer)
                .toList();

    }

    //ResponderPregunta(POST)
    @PostMapping("/answer/question/{questId}")
    public ResponseEntity<AnswerResponse> register(@Valid @RequestBody CreateAnswer newAnswer,
                                                    @AuthenticationPrincipal User user, @PathVariable Long questId){
        Question quest = questionService.findById(questId);
        Answer created = answerService.save(newAnswer, user, quest);
        questionService.addAnswer(quest, created);
        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity
                .created(createdURI)
                .body(AnswerResponse.fromAnswer(created));


    }


    //VerTodasLasRespuestasDeUnaPregunta
    @GetMapping("/answer/question/{id}")
    public List<AnswerResponse> findAllByQuest(@PathVariable Long id) {

        return answerService.findAllByQuestion(id)
                .stream()
                .map(AnswerResponse::fromAnswer)
                .toList();

    }

    //EliminarRespuesta(DELETE)

    @DeleteMapping("/answer/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        answerService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    //DarLike/Dislike respuesta


}

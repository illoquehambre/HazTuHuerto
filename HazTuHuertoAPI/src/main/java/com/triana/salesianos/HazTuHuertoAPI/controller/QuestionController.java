package com.triana.salesianos.HazTuHuertoAPI.controller;

import com.triana.salesianos.HazTuHuertoAPI.model.User;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.question.QuestionResponse;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.user.UserDetails;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.user.UserResponse;
import com.triana.salesianos.HazTuHuertoAPI.service.QuestionService;
import com.triana.salesianos.HazTuHuertoAPI.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

    //VerTodasLasPreguntasDETodosLosUsuarios

    @GetMapping("/question")
    public List<QuestionResponse> findAll() {

        return questionService.findAll()
                .stream()
                .map(QuestionResponse::fromQuestion)
                .toList();

    }

    //VerTodasLasPreguntasDEUnUsuario(GET)
    @GetMapping("/question/{username}")
    public List<QuestionResponse> findQuestListByUser(@PathVariable String username) {

            return questionService.findByUserName(username)
                .stream()
                .map(QuestionResponse::fromQuestion)
                .toList();
    }


    //Publicas pregunta (POST)
    //EliminarPregunta(DELETE)

    //FiltraPreguntasPorEtiquetas(SearchCriteria??)
    //DarLike/Dislike pregunta

}

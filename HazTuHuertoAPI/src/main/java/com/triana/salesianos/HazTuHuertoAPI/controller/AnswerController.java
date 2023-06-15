package com.triana.salesianos.HazTuHuertoAPI.controller;

import com.triana.salesianos.HazTuHuertoAPI.model.Answer;
import com.triana.salesianos.HazTuHuertoAPI.model.Question;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.PageDto;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.answer.AnswerResponse;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.answer.CreateAnswer;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.question.CreateQuestion;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.question.QuestionDetails;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.question.QuestionResponse;
import com.triana.salesianos.HazTuHuertoAPI.repository.UserRepository;
import com.triana.salesianos.HazTuHuertoAPI.search.util.SearchCriteria;
import com.triana.salesianos.HazTuHuertoAPI.search.util.SearchCriteriaExtractor;
import com.triana.salesianos.HazTuHuertoAPI.service.AnswerService;
import com.triana.salesianos.HazTuHuertoAPI.service.QuestionService;
import com.triana.salesianos.HazTuHuertoAPI.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@OpenAPIDefinition(info = @Info(title ="Haz-Tu-Huerto"))
@Tag(name = "Answers", description = "This class implements Restcontrollers for the Entity Answers")
@CrossOrigin("http://localhost:3000")
public class AnswerController {




    private final QuestionService questionService;
    private final AnswerService answerService;

    private final UserService userService;
    private final UserRepository userRepository;

  //No se usa

    @GetMapping("/answer")
    public PageDto<AnswerResponse> findByuserName(@RequestParam(value = "search", defaultValue = "") String search,
                                                  @PageableDefault(size = 20, page = 0) Pageable pageable) {

        List<SearchCriteria> params = SearchCriteriaExtractor.extractSearchCriteriaList(search);

        return answerService.search(params, pageable);

    }

    //VerTodasLasREspuestasDEUnUsuario(GET)
    @Operation(summary = "This method list all answers of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "At least one Answer was found",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AnswerResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                    {
                                                        "id": "2",
                                                        "content": "JD (Junk Dog) participa en combates cerrados de boxeo en un ring subterráneo para sobrevivir. Hoy, entra en el ring de nuevo, pero se encuentra con cierta persona. JD quiere aceptar el reto con el que lo arriesgará todo.",
                                                        "createdAt": "24/02/2023 11:51:12",
                                                        "publisher": "Programer12",
                                                        "question": {
                                                            "id": "1",
                                                            "title": "Duda existencial",
                                                            "content": "¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?",
                                                            "createdAt": "24/02/2023 10:58:25",
                                                            "publisher": {
                                                                "id": "ac1b0350-8682-1a48-8186-82db80fa0000",
                                                                "username": "illo2",
                                                                "avatar": "monke2.jpg",
                                                                "fullName": "illo que hambre",
                                                                "createdAt": "24/02/2023 10:57:48"
                                                            },
                                                            "score": 1,
                                                            "answers": 2
                                                        }
                                                    },
                                                    {
                                                        "id": "4",
                                                        "content": "JD (Junk Dog) participa en combates cerrados de boxeo en un ring subterráneo para sobrevivir. Hoy, entra en el ring de nuevo, pero se encuentra con cierta persona. JD quiere aceptar el reto con el que lo arriesgará todo.",
                                                        "createdAt": "24/02/2023 12:15:35",
                                                        "publisher": "Programer12",
                                                        "question": {
                                                            "id": "1",
                                                            "title": "Duda existencial",
                                                            "content": "¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?",
                                                            "createdAt": "24/02/2023 10:58:25",
                                                            "publisher": {
                                                                "id": "ac1b0350-8682-1a48-8186-82db80fa0000",
                                                                "username": "illo2",
                                                                "avatar": "monke2.jpg",
                                                                "fullName": "illo que hambre",
                                                                "createdAt": "24/02/2023 10:57:48"
                                                            },
                                                            "score": 1,
                                                            "answers": 2
                                                        }
                                                    }
                                                ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "Any Answer were found",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed to do this request",
                    content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "You are not allowed to do this request",
                    content = @Content),
    })

    @GetMapping("/answer/user/{name}")
    public List<AnswerResponse> findByuserName(@PathVariable String name) {//necesario al haber implementado search criteria?

        return answerService.findAllByUserName(name)
                .stream()
                .map(AnswerResponse::fromAnswer)
                .toList();

    }

    //ResponderPregunta(POST)
    @Operation(summary = "This method creates a new answer for an specific quest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "A new Answer was successfully created",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AnswerResponse.class)),
                            examples = @ExampleObject(value = """
                                    {
                                                        "id": "4",
                                                        "content": "JD (Junk Dog) participa en combates cerrados de boxeo en un ring subterráneo para sobrevivir. Hoy, entra en el ring de nuevo, pero se encuentra con cierta persona. JD quiere aceptar el reto con el que lo arriesgará todo.",
                                                        "createdAt": "24/02/2023 12:15:35",
                                                        "publisher": "Programer12",
                                                        "question": {
                                                            "id": "1",
                                                            "title": "Duda existencial",
                                                            "content": "¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?",
                                                            "createdAt": "24/02/2023 10:58:25",
                                                            "publisher": {
                                                                "id": "ac1b0350-8682-1a48-8186-82db80fa0000",
                                                                "username": "illo2",
                                                                "avatar": "monke2.jpg",
                                                                "fullName": "illo que hambre",
                                                                "createdAt": "24/02/2023 10:57:48"
                                                            },
                                                            "score": 1,
                                                            "answers": 2
                                                        }
                                                    }
                                    """)) }),
            @ApiResponse(responseCode = "400",
                    description = "The data submited have a wrong format",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed no do this request",
                    content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "You are not allowed no do this request",
                    content = @Content),
    })
    @PostMapping("/answer/question/{questId}")
    public ResponseEntity<AnswerResponse> register(@Valid @RequestBody CreateAnswer newAnswer,
                                                    @AuthenticationPrincipal User user, @PathVariable Long questId){
        User foundUser = userService.findById(user.getId());
        Question quest = questionService.findById(questId);
        Answer created = answerService.save(newAnswer, user, quest);
        foundUser.addAnswer(quest, created);
        userRepository.save(foundUser);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity
                .created(createdURI)
                .body(AnswerResponse.fromAnswer(created));


    }


    //VerTodasLasRespuestasDeUnaPregunta
    @Operation(summary = "This method list all answers of a specific quest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "At least one Answer was found",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AnswerResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                    {
                                                        "id": "2",
                                                        "content": "JD (Junk Dog) participa en combates cerrados de boxeo en un ring subterráneo para sobrevivir. Hoy, entra en el ring de nuevo, pero se encuentra con cierta persona. JD quiere aceptar el reto con el que lo arriesgará todo.",
                                                        "createdAt": "24/02/2023 11:51:12",
                                                        "publisher": "Programer12",
                                                        "question": {
                                                            "id": "1",
                                                            "title": "Duda existencial",
                                                            "content": "¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?",
                                                            "createdAt": "24/02/2023 10:58:25",
                                                            "publisher": {
                                                                "id": "ac1b0350-8682-1a48-8186-82db80fa0000",
                                                                "username": "illo2",
                                                                "avatar": "monke2.jpg",
                                                                "fullName": "illo que hambre",
                                                                "createdAt": "24/02/2023 10:57:48"
                                                            },
                                                            "score": 1,
                                                            "answers": 2
                                                        }
                                                    },
                                                    {
                                                        "id": "4",
                                                        "content": "JD (Junk Dog) participa en combates cerrados de boxeo en un ring subterráneo para sobrevivir. Hoy, entra en el ring de nuevo, pero se encuentra con cierta persona. JD quiere aceptar el reto con el que lo arriesgará todo.",
                                                        "createdAt": "24/02/2023 12:15:35",
                                                        "publisher": "Programer12",
                                                        "question": {
                                                            "id": "1",
                                                            "title": "Duda existencial",
                                                            "content": "¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?",
                                                            "createdAt": "24/02/2023 10:58:25",
                                                            "publisher": {
                                                                "id": "ac1b0350-8682-1a48-8186-82db80fa0000",
                                                                "username": "illo2",
                                                                "avatar": "monke2.jpg",
                                                                "fullName": "illo que hambre",
                                                                "createdAt": "24/02/2023 10:57:48"
                                                            },
                                                            "score": 1,
                                                            "answers": 2
                                                        }
                                                    }
                                                ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "Any Answer were found",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed to do this request",
                    content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "You are not allowed to do this request",
                    content = @Content),
    })
    @GetMapping("/answer/question/{id}")
    public List<AnswerResponse> findAllByQuest(@PathVariable Long id) {

        return answerService.findAllByQuestion(id)
                .stream()
                .map(AnswerResponse::fromAnswer)
                .toList();

    }

    //EliminarRespuesta(DELETE)
    @Operation(summary = "This method delete a specific answer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "No content ",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed to do this request",
                    content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "You are not allowed to do this request",
                    content = @Content),
    })
    @DeleteMapping("/answer/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal User user,@PathVariable Long id) {
        //if(user.equals(answerService.findById(id).getPublisher()))
        if(userService.checkUserLogedInAnswer(user.getId(), id))
            answerService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    //DarLike/Dislike respuesta


}

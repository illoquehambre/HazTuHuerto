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
import io.swagger.v3.oas.annotations.info.Info;
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


    @GetMapping("/answer")
    public PageDto<AnswerResponse> findByuserName(@RequestParam(value = "search", defaultValue = "") String search,
                                                  @PageableDefault(size = 20, page = 0) Pageable pageable) {

        List<SearchCriteria> params = SearchCriteriaExtractor.extractSearchCriteriaList(search);

        return answerService.search(params, pageable);

    }

    //VerTodasLasREspuestasDEUnUsuario(GET)

    @GetMapping("/answer/user/{name}")
    public List<AnswerResponse> findByuserName(@PathVariable String name) {//necesario al haber implementado search criteria?

        return answerService.findAllByUserName(name)
                .stream()
                .map(AnswerResponse::fromAnswer)
                .toList();

    }

    //ResponderPregunta(POST)
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
    @GetMapping("/answer/question/{id}")
    public List<AnswerResponse> findAllByQuest(@PathVariable Long id) {

        return answerService.findAllByQuestion(id)
                .stream()
                .map(AnswerResponse::fromAnswer)
                .toList();

    }

    //EliminarRespuesta(DELETE)

    @DeleteMapping("/answer/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal User user,@PathVariable Long id) {
        //if(user.equals(answerService.findById(id).getPublisher()))
        if(userService.checkUserLogedInAnswer(user.getId(), id))
            answerService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    //DarLike/Dislike respuesta


}

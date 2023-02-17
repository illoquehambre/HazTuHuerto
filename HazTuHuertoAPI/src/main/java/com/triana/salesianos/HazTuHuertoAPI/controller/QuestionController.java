package com.triana.salesianos.HazTuHuertoAPI.controller;

import com.triana.salesianos.HazTuHuertoAPI.model.Question;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.PageDto;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.question.CreateQuestion;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.question.QuestionDetails;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.question.QuestionResponse;
import com.triana.salesianos.HazTuHuertoAPI.repository.QuestionRepository;
import com.triana.salesianos.HazTuHuertoAPI.search.util.SearchCriteria;
import com.triana.salesianos.HazTuHuertoAPI.search.util.SearchCriteriaExtractor;
import com.triana.salesianos.HazTuHuertoAPI.service.QuestionService;
import com.triana.salesianos.HazTuHuertoAPI.service.UserService;
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
import java.util.Optional;

@RestController
@AllArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;
    private final QuestionRepository questionRepository;

    //VerTodasLasPreguntasDETodosLosUsuarios

    @GetMapping("/question")
    public PageDto<QuestionResponse> findAll(@RequestParam(value = "search", defaultValue = "") String search,
                                             @PageableDefault(size = 20, page = 0) Pageable pageable) {

        List<SearchCriteria> params = SearchCriteriaExtractor.extractSearchCriteriaList(search);

        return questionService.search(params, pageable);

    }

    //VerTodasLasPreguntasDEUnUsuario(GET)
    @GetMapping("/question/user/{userName}")//Imnplementar con search criteria??
    public List<QuestionResponse> findQuestListByUser(@PathVariable String userName) {

            return questionService.findByUserName(userName)
                .stream()
                .map(QuestionResponse::fromQuestion)
                .toList();
    }

    //Ver una pregunta por id
    @GetMapping("/question/{id}")
    public QuestionDetails findQuestById(@PathVariable Long id) {

        return QuestionDetails.fromQuestion(questionService.findById(id));

    }

    //Publicas pregunta (POST)

    @PostMapping("/question")
    public ResponseEntity<QuestionDetails> register(@Valid @RequestBody CreateQuestion newQuest, @AuthenticationPrincipal User user) {

        Question created = questionService.save(newQuest, user);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity
                .created(createdURI)
                .body(QuestionDetails.fromQuestion(created));


    }

    //EliminarPregunta(DELETE)

    @DeleteMapping("/question/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal User user,@PathVariable Long id) {
        //Question quest=questionService.findById(id);
        if(userService.checkUserLogedInQuestion(user.getId(), id))
            questionService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/admin/question/{id}")
    public ResponseEntity<?> adminDelete(@PathVariable Long id) {

        questionService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    //FiltraPreguntasPorEtiquetas(SearchCriteria??)
    //DarLike/Dislike (vamo a dejar esto pal final)
    @PostMapping("/question/{id}/like")
    public QuestionDetails likePost(@AuthenticationPrincipal User user, @PathVariable Long id) {

        Question found = questionService.findById(id);
        Question modified = questionService.likeQuestion(user,found);

        return QuestionDetails.fromQuestion(modified);

    }



}

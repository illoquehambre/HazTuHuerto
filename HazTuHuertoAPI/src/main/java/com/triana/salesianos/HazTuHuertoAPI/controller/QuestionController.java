package com.triana.salesianos.HazTuHuertoAPI.controller;

import com.triana.salesianos.HazTuHuertoAPI.model.Question;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.PageDto;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.question.CreateQuestion;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.question.QuestionDetails;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.question.QuestionResponse;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.user.EditUser;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.user.UserDetailsDto;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.user.UserResponse;
import com.triana.salesianos.HazTuHuertoAPI.repository.QuestionRepository;
import com.triana.salesianos.HazTuHuertoAPI.repository.UserRepository;
import com.triana.salesianos.HazTuHuertoAPI.search.util.SearchCriteria;
import com.triana.salesianos.HazTuHuertoAPI.search.util.SearchCriteriaExtractor;
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
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Optional;

class QuestionUserResponse extends PageDto<QuestionResponse>{}

@RestController
@AllArgsConstructor
@OpenAPIDefinition(info = @Info(title ="Haz-Tu-Huerto"))
@Tag(name = "Question", description = "This class implements Restcontrollers for the Entity Question")
@CrossOrigin("http://localhost:3000")
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    //VerTodasLasPreguntasDETodosLosUsuarios

    @Operation(summary = "This method list all quests")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "At least one Quest was found",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = QuestionUserResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                  "content": [
                                                      {
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
                                                          "score": 0,
                                                          "answers": 0
                                                      }
                                                  ],
                                                  "last": true,
                                                  "first": true,
                                                  "totalPages": 1,
                                                  "totalElements": 1
                                              }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "Any Quest were found",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed to do this request",
                    content = @Content),
    })
    @GetMapping("/question")
    public PageDto<QuestionResponse> findAll(@RequestParam(value = "search", defaultValue = "") String search,
                                             @PageableDefault(size = 10, page = 0) Pageable pageable) {

        List<SearchCriteria> params = SearchCriteriaExtractor.extractSearchCriteriaList(search);

        return questionService.search(params, pageable);

    }

    //VerTodasLasPreguntasDEUnUsuario(GET)
    @Operation(summary = "This method list all quests of a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "At least one Quest was found",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = QuestionResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                  "content": [
                                                      {
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
                                                          "score": 0,
                                                          "answers": 0
                                                      }
                                                  ],
                                                  "last": true,
                                                  "first": true,
                                                  "totalPages": 1,
                                                  "totalElements": 1
                                              }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "Any Quest were found",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed to do this request",
                    content = @Content),
    })
    @GetMapping("/question/user/{userName}")//Imnplementar con search criteria??
    public List<QuestionResponse> findQuestListByUser(@PathVariable String userName) {

            return questionService.findByUserName(userName)
                .stream()
                .map(QuestionResponse::fromQuestion)
                .toList();
    }

    //Ver una pregunta por id
    @Operation(summary = "This method find a quest by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "the user was successfully found",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = QuestionDetails.class)),
                            examples = @ExampleObject(value = """
                                    {
                                          "id": "1",
                                          "title": "Duda existencial",
                                          "content": "¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?",
                                          "urlImg": "Pasen_705609.png",
                                          "createdAt": "24/02/2023 10:58:25",
                                          "publisher": "illo2",
                                          "score": 0,
                                          "answers": [
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
                                                      "score": 0,
                                                      "answers": 1
                                                  }
                                              }
                                          ],
                                          "likedByLoguedUser": false
                                      }
                                    """)) }),
            @ApiResponse(responseCode = "404",
                    description = "The quest was not found",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed to do this request",
                    content = @Content),
    })
    @GetMapping("/question/{id}")
    public QuestionDetails findQuestById(@AuthenticationPrincipal User user,@PathVariable Long id) {

        Boolean liked = userService.checkUserLiked(user.getId(), id);
        return QuestionDetails.fromQuestion(questionService.findById(id), liked);

    }

    //Publicas pregunta (POST)
    @Operation(summary = "This method creates a new quest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "A new Quest was successfully created",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = QuestionDetails.class)),
                            examples = @ExampleObject(value = """
                                    {
                                          "id": "3",
                                          "title": "Duda existencial",
                                          "content": "¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?",
                                          "urlImg": "Pasen_991555.png",
                                          "createdAt": "24/02/2023 11:53:11",
                                          "publisher": "Programer12",
                                          "score": 0,
                                          "answers": [],
                                          "likedByLoguedUser": false
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

    @PostMapping("/question")
    public ResponseEntity<QuestionDetails> register(@RequestPart("file") MultipartFile file,
                                                    @Valid @RequestPart("newQuest") CreateQuestion newQuest,
                                                    @AuthenticationPrincipal User user) {
        User userFound=userService.findById(user.getId());
        Question created = questionService.save(newQuest, user, file);
        userFound.addQuestion(created);
        userRepository.save(userFound);
        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity
                .created(createdURI)
                .body(QuestionDetails.fromQuestion(created));


    }
    @Operation(summary = "This method delete a quest and the answers of it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "No content ",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed to do this request",
                    content = @Content),
    })
    //EliminarPregunta(DELETE)

    @DeleteMapping("/question/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal User user,@PathVariable Long id) {
        //Question quest=questionService.findById(id);
        if(userService.checkUserLogedInQuestion(user.getId(), id))
            questionService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "This method delete a quest and the answers of it by an admin user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "No content ",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed to do this request",
                    content = @Content),
    })
    @DeleteMapping("/admin/question/{id}")
    public ResponseEntity<?> adminDelete(@PathVariable Long id) {

        questionService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    //FiltraPreguntasPorEtiquetas(SearchCriteria??)
    //DarLike/Dislike (vamo a dejar esto pal final)
    @Operation(summary = "This method give/quit a like to a question by the logged user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "A Like was successfully given/quit ",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = QuestionDetails.class)),
                            examples = @ExampleObject(value = """
                                    {
                                        "id": "1",
                                        "title": "Duda existencial",
                                        "content": "¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?¿Son los pájaros reales?",
                                        "urlImg": "Pasen_705609.png",
                                        "createdAt": "24/02/2023 10:58:25",
                                        "publisher": "illo2",
                                        "score": 1,
                                        "answers": [
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
                                                    "answers": 1
                                                }
                                            }
                                        ],
                                        "likedByLoguedUser": true
                                    }
                                    """)) }),
            @ApiResponse(responseCode = "404",
                    description = "The Quest was not found",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed to do this method",
                    content = @Content),
    })
    @PostMapping("/question/{id}/like")
    public QuestionDetails likePost(@AuthenticationPrincipal User user, @PathVariable Long id) {//Deberia devolver un REsponseEntity y crear una Uri

        Question found = questionService.findById(id);
        Question modified = questionService.likeQuestion(user,found);
        Boolean liked = userService.checkUserLiked(user.getId(), id);
        return QuestionDetails.fromQuestion(modified, liked);

    }
//No se llega a usar
    @GetMapping(
            value = "question/{id}/file",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody byte[] getFile(@PathVariable Long id) throws IOException {
        Question quest=questionService.findById(id);
        InputStream in = getClass()
                .getResourceAsStream("/com/baeldung/produceimage/"+quest.getUrlImg());
        assert in != null;
        return IOUtils.toByteArray(in);
    }



}

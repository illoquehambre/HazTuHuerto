package com.triana.salesianos.HazTuHuertoAPI.model.dto.question;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.triana.salesianos.HazTuHuertoAPI.model.Answer;
import com.triana.salesianos.HazTuHuertoAPI.model.Question;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.answer.AnswerResponse;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.user.UserResponse;
import com.triana.salesianos.HazTuHuertoAPI.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDetails {


    protected String id;

    protected String title, content, urlImg;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    protected LocalDateTime createdAt;

    protected UserResponse publisher;
    //Esto será calculable entre likes y dislikes de alguna manera
    protected int score;

    protected List<AnswerResponse> answers;

    protected Boolean likedByLoguedUser;//El valor de este atributo dependerá de si el usuario logueado le ha dado o no like

    public static QuestionDetails fromQuestion(Question quest) {

        return QuestionDetails.builder()
                .id(quest.getId().toString())
                .title(quest.getTitle())
                .content(quest.getContent())
                .publisher(UserResponse.fromUser(quest.getPublisher()))
                .createdAt(quest.getCreatedAt())
                .score(quest.getLikes()==null||quest.getLikes().isEmpty()?0:quest.getLikes().size())
                .urlImg(quest.getUrlImg())
                .answers(quest.getAnswers().stream().map(AnswerResponse::fromAnswer).toList())
                .likedByLoguedUser(false)
                .build();
    }
    public static QuestionDetails fromQuestion(Question quest,Boolean like) {

        return QuestionDetails.builder()
                .id(quest.getId().toString())
                .title(quest.getTitle())
                .content(quest.getContent())
                .publisher(UserResponse.fromUser(quest.getPublisher()))
                .createdAt(quest.getCreatedAt())
                .score(quest.getLikes()==null||quest.getLikes().isEmpty()?0:quest.getLikes().size())
                .urlImg(quest.getUrlImg())
                .answers(quest.getAnswers().stream().map(AnswerResponse::fromAnswer).toList())
                .likedByLoguedUser(like)
                .build();
    }
}

package com.triana.salesianos.HazTuHuertoAPI.model.dto.answer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.triana.salesianos.HazTuHuertoAPI.model.Answer;
import com.triana.salesianos.HazTuHuertoAPI.model.Question;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.question.QuestionResponse;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerResponse {
    protected String id;

    protected String  content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    protected LocalDateTime createdAt;

    private String publisher;
    //Esto será calculable entre likes y dislikes de alguna manera
    private QuestionResponse question;

    public static AnswerResponse fromAnswer(Answer answer) {

        return AnswerResponse.builder()
                .id(answer.getId().toString())
                .content(answer.getContent())
                .publisher(answer.getPublisher().getUsername())
                .createdAt(answer.getCreatedAt())
                .question(QuestionResponse.fromQuestion(answer.getQuestion()))
                .build();
    }


}

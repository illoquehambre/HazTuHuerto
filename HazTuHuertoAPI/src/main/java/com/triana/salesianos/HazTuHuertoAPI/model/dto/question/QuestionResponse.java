package com.triana.salesianos.HazTuHuertoAPI.model.dto.question;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.triana.salesianos.HazTuHuertoAPI.model.Question;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class QuestionResponse {

    protected String id;

    protected String title, content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    protected LocalDateTime createdAt;

    private User publisher;
    //Esto será calculable entre likes y dislikes de alguna manera
    private int score;

    public static QuestionResponse fromQuestion(Question quest) {

        return QuestionResponse.builder()
                .id(quest.getId().toString())
                .title(quest.getTitle())
                .content(quest.getContent())
                .publisher(quest.getPublisher())
                .createdAt(quest.getCreatedAt())
                .score(quest.getLikes().size()-quest.getDislikes().size())
                .build();
    }

}

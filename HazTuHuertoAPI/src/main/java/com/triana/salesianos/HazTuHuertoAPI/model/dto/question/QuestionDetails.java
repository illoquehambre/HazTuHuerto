package com.triana.salesianos.HazTuHuertoAPI.model.dto.question;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.triana.salesianos.HazTuHuertoAPI.model.Answer;
import com.triana.salesianos.HazTuHuertoAPI.model.Question;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
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

    private User publisher;
    //Esto será calculable entre likes y dislikes de alguna manera
    private int score;

    private List<Answer> answers;

    public static QuestionDetails fromQuestion(Question quest) {

        return QuestionDetails.builder()
                .id(quest.getId().toString())
                .title(quest.getTitle())
                .content(quest.getContent())
                .publisher(quest.getPublisher())
                .createdAt(quest.getCreatedAt())
                .score(quest.getLikes().size())
                .urlImg(quest.getUrlImg())
                .answers(quest.getAnswers())
                .build();
    }
}

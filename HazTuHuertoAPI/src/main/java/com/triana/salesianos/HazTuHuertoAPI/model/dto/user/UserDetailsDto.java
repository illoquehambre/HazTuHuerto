package com.triana.salesianos.HazTuHuertoAPI.model.dto.user;

import com.triana.salesianos.HazTuHuertoAPI.model.Answer;
import com.triana.salesianos.HazTuHuertoAPI.model.Question;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserDetailsDto extends UserResponse{


    private List<Question> publishedQuestions;

    private List<Answer> publishedAnswers;

    private List<Question> favPosts;//Lo vamos a dejar por ahora

    //Escalable conforme recibe likes en preguntas o respuestas.
    private int reputation;
    private String email;



    public static UserDetailsDto fromUser(User user) {

        return UserDetailsDto.builder()
                .id(user.getId().toString())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .fullName(user.getFullName())
                .createdAt(user.getCreatedAt())
                .publishedQuestions(user.getPublishedQuestions())
                .publishedAnswers(user.getPublishedAnswers())
                .favPosts(user.getFavPosts())
                .reputation(user.getReputation())
                .email(user.getEmail())
                .build();
    }
}

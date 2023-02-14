package com.triana.salesianos.HazTuHuertoAPI.model.dto.user;

import com.triana.salesianos.HazTuHuertoAPI.model.Answer;
import com.triana.salesianos.HazTuHuertoAPI.model.Question;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserDetails extends UserResponse{


    private List<Question> publishedQuestions;


    private List<Answer> publishedAnswers;

    private List<Question> favPosts;//Lo vamos a dejar por ahora

    //Escalable conforme recibe likes en preguntas o respuestas.
    private int reputation;



    public static UserDetails fromUser(User user) {

        return UserDetails.builder()
                .id(user.getId().toString())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .fullName(user.getFullName())
                .createdAt(user.getCreatedAt())
                .publishedQuestions(user.getPublishedQuestions())
                .publishedAnswers(user.getPublishedAnswers())
                .favPosts(user.getFavPosts())
                .reputation(user.getReputation())
                .build();
    }
}

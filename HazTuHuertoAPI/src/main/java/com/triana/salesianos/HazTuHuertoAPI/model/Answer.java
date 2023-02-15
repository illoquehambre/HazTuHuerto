package com.triana.salesianos.HazTuHuertoAPI.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name="Comentary")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Answer {


    private String content, urlImg;

    @Id@GeneratedValue
    private Long id;

    @ManyToOne
    private Question question;

    @ManyToOne(cascade = CascadeType.ALL)
    private User publisher;

    @ManyToMany
    private List<User> likes;//unidi, ¿bajo acoplamiento?

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
}

package com.triana.salesianos.HazTuHuertoAPI.model;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
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
    private User user;

    @ManyToMany
    private List<User> likes;//unidi, ¿bajo acoplamiento?
    @ManyToMany
    private List<User> dislikes;//unidi, ¿bajo acoplamiento?
}

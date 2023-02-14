package com.triana.salesianos.HazTuHuertoAPI.model;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity(name="Post")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Question {

    private String title, content, urlImg;

    @Id@GeneratedValue
    private Long id;//Hay que cambiarlo por un UUID

    @ManyToOne
    @JoinColumn(name = "owner_id", foreignKey = @ForeignKey(name= "FK_USER_POST"))
    private User publisher;

    @ManyToMany
    private List<User> likes;//unidi, ¿bajo acoplamiento?
    @ManyToMany
    private List<User> dislikes;//unidi, ¿bajo acoplamiento?


    @OneToMany
    private  List<Answer> answers;


}

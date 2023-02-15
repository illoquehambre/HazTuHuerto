package com.triana.salesianos.HazTuHuertoAPI.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private Long id;//Hay que cambiarlo por un UUID??

    @ManyToOne
    @JoinColumn(name = "owner_id", foreignKey = @ForeignKey(name= "FK_USER_POST"))
    private User publisher;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    protected LocalDateTime createdAt;

    @ManyToMany
    private List<User> likes;//unidi, ¿bajo acoplamiento?

    @OneToMany
    private  List<Answer> answers;


}

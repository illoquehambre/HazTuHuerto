package com.triana.salesianos.HazTuHuertoAPI.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name="Question")
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
    @JsonIgnore
    private User publisher;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @CreatedDate
    protected LocalDateTime createdAt;

    @ManyToMany
    private List<User> likes;//unidi, ¿bajo acoplamiento?

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)//Ojo piojo
    private  List<Answer> answers;




}

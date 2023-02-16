package com.triana.salesianos.HazTuHuertoAPI.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name="Answer")
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

    @JoinColumn(name = "question_id", foreignKey = @ForeignKey(name= "FK_QUESTION_ANSWER"))
    @ManyToOne
    @JsonIgnore
    private Question question;

    @JoinColumn(name = "owner_id", foreignKey = @ForeignKey(name= "FK_USER_ANSWER"))
    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonIgnore
    private User publisher;

    @ManyToMany
    @Builder.Default
    private List<User> likes=new ArrayList<>();//unidi, ¿bajo acoplamiento?

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @CreatedDate
    protected LocalDateTime createdAt;

    @PreRemove
    public void preRemoveAnswer() {
        question.getAnswers().remove(this);
        this.publisher = null;
        likes=null;
        question=null;
    }
}

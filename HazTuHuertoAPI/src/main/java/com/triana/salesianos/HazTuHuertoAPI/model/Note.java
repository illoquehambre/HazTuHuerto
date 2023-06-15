package com.triana.salesianos.HazTuHuertoAPI.model;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name="Note")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private LocalDate lastModifiedDate;
    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "cultivation_id", foreignKey = @ForeignKey(name= "FK_NOTE_CULTIVATION"))

    private Cultivation  cultivation;


}

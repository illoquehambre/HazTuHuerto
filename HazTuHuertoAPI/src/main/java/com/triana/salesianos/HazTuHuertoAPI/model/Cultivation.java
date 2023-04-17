package com.triana.salesianos.HazTuHuertoAPI.model;

import com.triana.salesianos.HazTuHuertoAPI.validation.annotation.FieldsValueMatch;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity(name="Cultivation")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cultivation {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patch_id", foreignKey = @ForeignKey(name= "FK_CULTIVATION_PATCH"))
    private Patch patch;

    private String name;
    private String img;
    private String variety;
    private LocalDate plantingDate;
    private LocalDate harvestingDate;
    @OneToMany
    private List<Note> noteList;

}

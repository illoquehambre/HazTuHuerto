package com.triana.salesianos.HazTuHuertoAPI.model;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity(name="Comentary")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comentary {

    @Id@GeneratedValue
    private Long id;

    @ManyToOne
    private Post post;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    //Plantear función de likes
    //Plantear sobre la posibilidad de comentar en un comentario
}

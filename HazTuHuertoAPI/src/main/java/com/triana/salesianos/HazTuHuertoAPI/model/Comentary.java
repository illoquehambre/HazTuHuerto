package com.triana.salesianos.HazTuHuertoAPI.model;

import lombok.*;

import javax.persistence.*;

@Entity(name="Comentary")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comentary {

    @Id@GeneratedValue
    private Long id;//Hay que cambiarlo por un UUID

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;

    //Plantear función de likes
    //Plantear sobre la posibilidad de comentar en un comentario
}

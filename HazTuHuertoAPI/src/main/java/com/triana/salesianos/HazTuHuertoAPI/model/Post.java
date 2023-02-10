package com.triana.salesianos.HazTuHuertoAPI.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity(name="Post")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {

    @Id@GeneratedValue
    private Long id;//Hay que cambiarlo por un UUID

    @ManyToOne
    @JoinColumn(name = "owner_id", foreignKey = @ForeignKey(name= "FK_USER_POST"))
    private User publisher;

    @OneToMany
    private List<User> likes;

    @OneToMany
    private  List<Comentary> comentaries;


}

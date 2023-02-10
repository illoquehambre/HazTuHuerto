package com.triana.salesianos.HazTuHuertoAPI.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity(name="User")
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {


    @Id@GeneratedValue
    private Long id;//Hay que cambiarlo por un UUID

    private String fullName, username, password, verifyPassword;

    private LocalDate birthDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @CreationTimestamp
    private LocalDateTime resgistrationDate;

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY)//Bidi
    private List<Post> posts;

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY)//Unidi
    private List<Post> favPosts;

    @OneToMany
    private List<Comentary> comentaries;

    @ElementCollection
    private List<User> followers;
    @ElementCollection
    private List<User> follows;


    public void toFollow(User userToFollow){
        if(!userToFollow.followers.contains(this)&&!this.follows.contains(userToFollow)){
            userToFollow.followers.add(this);
            this.follows.add(userToFollow);
        }else{
            //Aqui se lanzaría una excepcion pa indidcar que ya lo sigues
        }
    }



}

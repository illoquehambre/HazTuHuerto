package com.triana.salesianos.HazTuHuertoAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity(name="Vegetable_Garden")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VegetableGarden {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)//Ojo piojo
    private List<Patch> patchList;

    @JoinColumn(name = "owner_id", foreignKey = @ForeignKey(name= "FK_USER_VEGETABLEGARDEN"))
    @ManyToOne
    @JsonIgnore
    private User owner;

    private String name;
    private String latitude;
    private String longitude ;
    private String img;

    //Las alertas deben ser una clase??un atributo??? como coño hago esto??
    //Idea 1
        //Las alertas de tiempo son a una Api externa y no tiene nada que ver
        // Las alertas serán una interfaz que implementaran dos clases, una alrma diaria y otra de fecha especifica.
        //La alerta diaria contará con un atributo de horas minutos y un método que la comprobará
        // (deben hacerse multiples líneas de ejecución para una que compruebe el tiempo a cada cambio de minuto?)
        //La alerta concreta funcionará con una fecha sin hora y un método que hará una comprobación de cuanto queda


    public void addPatch(Patch patch){
        this.getPatchList().add(patch);
    }





}

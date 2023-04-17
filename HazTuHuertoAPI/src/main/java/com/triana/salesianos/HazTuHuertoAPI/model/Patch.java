package com.triana.salesianos.HazTuHuertoAPI.model;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity(name="Patch")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Patch {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Cultivation cultivation;
    //Con este atributo tenemos un pequeño problema a gestionar, ya que en principio en la clase cultivo solo hay
    //una parcela a la que pertenezca. Como lógica e negocio una msima estancia de un cultivo no puede estar en ambos
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)//Ojo piojo
    private List<Cultivation> cultivationHistory;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)//Ojo piojo
    @JoinColumn(name = "huerto_id", foreignKey = @ForeignKey(name= "FK_HUERTO_PARCELA"))
    private VegetableGarden garden;

    private String name;


}

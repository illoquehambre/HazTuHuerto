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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Cultivation cultivation;
    //Con este atributo tenemos un pequeño problema a gestionar, ya que en principio en la clase cultivo solo hay
    //una parcela a la que pertenezca. Como lógica e negocio una msima estancia de un cultivo no puede estar en ambos
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)//Ojo piojo
    private List<Cultivation> cultivationHistory;

    @ManyToOne(fetch = FetchType.LAZY)//Ojo piojo
    @JoinColumn(name = "garden_id", foreignKey = @ForeignKey(name= "FK_GARDEN_PATCH"))
    private VegetableGarden garden;

    private String name;

    public Cultivation addCultivation(Cultivation cult) {
        this.setCultivation(cult);
        cult.setPatch(this);

        return cult;

    }




}

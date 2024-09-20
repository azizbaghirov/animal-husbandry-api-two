package az.eagro.animalhusbandry.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "AnimalType")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimalTypeEntity {

    @Id
    @Column(name = "AnimalTypeId", nullable = false, insertable = false, updatable = false, unique = true)
    private Integer id;

    @Column(name = "Name", nullable = false, insertable = false, updatable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "FarmTypeId", nullable = false, insertable = false, updatable = false)
    private BreedingAnimalFarmTypeEntity farmType;

    @Column(name = "AnimalType")
    private Integer animalType;

}

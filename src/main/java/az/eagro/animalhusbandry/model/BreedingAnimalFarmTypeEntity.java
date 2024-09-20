package az.eagro.animalhusbandry.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "BreedingAnimalFarmType")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BreedingAnimalFarmTypeEntity {

    @Id
    @Column(name = "FarmTypeId", nullable = false, insertable = false, updatable = false, unique = true)
    private Integer id;

    @Column(name = "Name", nullable = false, insertable = false, updatable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "Label", nullable = false, insertable = false, updatable = false, unique = true)
    private BreedingAnimalFarmType label;

    @Column(name = "AnimalCategory")
    private Integer animalCategory;

}

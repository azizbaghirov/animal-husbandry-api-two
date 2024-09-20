package az.eagro.animalhusbandry.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "BreedingAnimalFarmSpecialization")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BreedingAnimalFarmSpecializationEntity {

    @Id
    @Column(name = "FarmSpecializationId", nullable = false, insertable = false, updatable = false, unique = true)
    private Integer id;

    @Column(name = "Name", nullable = false, insertable = false, updatable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "Label", nullable = false, insertable = false, updatable = false)
    private BreedingAnimalFarmSpecializationType label;

    @ManyToOne(optional = true)
    @JoinColumn(name = "FarmTypeId", nullable = true, insertable = false, updatable = false)
    private BreedingAnimalFarmTypeEntity farmType;

    @Column(name = "AnimalProductivityDirection")
    private Integer animalProductivityDirection;

}

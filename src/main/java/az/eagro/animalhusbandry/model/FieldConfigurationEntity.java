package az.eagro.animalhusbandry.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "FieldConfiguration")
@Entity
public class FieldConfigurationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FieldConfigurationId", nullable = false, updatable = false, unique = true)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "FarmTypeId", nullable = false)
    private BreedingAnimalFarmTypeEntity farmType;

    @ManyToOne
    @JoinColumn(name = "FarmSpecializationId", insertable = false, updatable = false)
    private BreedingAnimalFarmSpecializationEntity farmSpecialization;

    @ManyToOne
    @JoinColumn(name = "AnimalTypeId", insertable = false, updatable = false)
    private AnimalTypeEntity animalType;

}

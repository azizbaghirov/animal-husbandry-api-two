package az.eagro.animalhusbandry.api.service.model;

import az.eagro.animalhusbandry.model.BreedingAnimalFarmType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BreedingAnimalFarmTypeDTO {

    private Integer id;
    private String name;
    private BreedingAnimalFarmType label;

}

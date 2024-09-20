package az.eagro.animalhusbandry.api.service.model;

import az.eagro.animalhusbandry.api.PageRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindBreedingAnimalFarmDTO extends PageRequest {
    private Integer regionId;
    private Integer farmTypeId;
}

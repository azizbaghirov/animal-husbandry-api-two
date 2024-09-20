package az.eagro.animalhusbandry.api.service.model;

import az.eagro.animalhusbandry.model.BreedingAnimalFarmActivityType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BreedingAnimalFarmDTO {
    private Integer id;
    private FarmSpecializationDTO farmSpecialization;
    private BreedingAnimalFarmActivityType activityType;
    private AdministrativeAreaDTO administrativeArea;
    private String currentAddress;
    private AnimalSortDTO animalSort;
    private AnimalTypeDTO animalType;
    private boolean deletable;
}

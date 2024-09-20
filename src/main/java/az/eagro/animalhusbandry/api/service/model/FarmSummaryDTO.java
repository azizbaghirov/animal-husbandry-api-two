package az.eagro.animalhusbandry.api.service.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FarmSummaryDTO {

    private AdministrativeAreaDTO administrativeArea;
    private RegionDTO region;
    private BreedingAnimalFarmTypeDTO farmType;
    private AnimalTypeDTO animalType;
    private AnimalSortDTO animalSort;

}

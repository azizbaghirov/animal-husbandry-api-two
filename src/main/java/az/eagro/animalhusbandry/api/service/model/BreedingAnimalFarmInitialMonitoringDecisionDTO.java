package az.eagro.animalhusbandry.api.service.model;

import az.eagro.animalhusbandry.model.BreedingAnimalFarmActivityType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BreedingAnimalFarmInitialMonitoringDecisionDTO {

    private AdministrativeAreaDTO administrativeArea;
    private RegionDTO region;
    private BreedingAnimalFarmTypeDTO farmType;
    private BreedingAnimalFarmActivityType activityType;
    private FarmSpecializationDTO farmSpecialization;

}

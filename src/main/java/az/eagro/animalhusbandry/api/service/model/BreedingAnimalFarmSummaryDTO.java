package az.eagro.animalhusbandry.api.service.model;

import az.eagro.animalhusbandry.model.BreedingAnimalFarmActivityType;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BreedingAnimalFarmSummaryDTO {

    private AdministrativeAreaDTO administrativeArea;
    private RegionDTO region;
    private BreedingAnimalFarmTypeDTO farmType;
    private BreedingAnimalFarmActivityType activityType;
    private List<FieldDocumentSummaryDTO> fieldDocuments = new ArrayList<>();
    private FarmSpecializationDTO farmSpecialization;
    private String currentAddress;

}

package az.eagro.animalhusbandry.api.service.model;

import az.eagro.animalhusbandry.api.PageRequest;
import az.eagro.animalhusbandry.model.ApplicationStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindApplicationDTO extends PageRequest {

    private Integer farmSpecId;
    private Integer administrativeArea;
    private Integer animalTypeId;
    private String animalSortId;
    private ApplicationStatus applicationStatus;

}

package az.eagro.animalhusbandry.api.service.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MeasurementUnitDTO {

    private Integer id;
    private String label;
    private String name;
    private String description;

}

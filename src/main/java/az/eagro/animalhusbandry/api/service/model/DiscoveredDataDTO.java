package az.eagro.animalhusbandry.api.service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscoveredDataDTO {

    private Integer id;
    private FieldValueDTO declaredValue;
    private FieldValueDTO discoveredValue;
    private boolean valuesEqual;

}

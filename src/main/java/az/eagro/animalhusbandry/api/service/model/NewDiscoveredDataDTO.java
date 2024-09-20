package az.eagro.animalhusbandry.api.service.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NewDiscoveredDataDTO {

    private Integer declaredValueId;
    private NewFieldValueDTO discoveredValue;
    private boolean valuesEqual;

}

package az.eagro.animalhusbandry.api.service.model;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewFieldValueDTO {

    private Integer fieldId;
    private Integer intValue;
    private BigDecimal decimalValue;
    private Boolean booleanValue;
    private String textValue;

}

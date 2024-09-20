package az.eagro.animalhusbandry.api.service.model;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldDocumentApplicationDTO {

    private String registrationNumber;
    private String documentClassification;
    private String documentType;
    private BigDecimal spaceHa;

}

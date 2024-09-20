package az.eagro.animalhusbandry.feign.model;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReferencedDocumentDTO {

    private Integer id;
    private BigDecimal spaceHa;
    private String registryNumber;
    private String registrationNumber;
    private String journalNumber;
    private String regionCode;
    private DocumentTypeDTO documentType;

}

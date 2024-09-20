package az.eagro.animalhusbandry.api.service.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldDocumentSummaryDTO {

    private Integer id;
    private Integer fieldId;
    private String fieldName;
    private String registryNumber;
    private String registrationNumber;
    private String referencedDocumentTypeName;
    private String referencedDocumentTypeLabel;
    private String documentTypeName;
    private String documentTypeLabel;
    private String documentClassificationLabel;
    private String documentClassificationName;
    private String journalNumber;
    private String regionCode;
    private BigDecimal spaceHa;
    private String referencedRegistryNumber;
    private String referencedRegistrationNumber;
    private String referencedJournalNumber;
    private String referencedRegionCode;

}

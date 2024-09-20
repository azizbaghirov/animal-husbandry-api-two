package az.eagro.animalhusbandry.feign.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DocumentDTO {

    @JsonProperty("id")
    private Integer documentId;

    private String journalNumber;

    private String regionCode;

    private String registryNumber;

    private String registrationNumber;

    private String contractNumber;

    @NotNull(message = "Sənəd növü vacib məlumatdır")
    private DocumentTypeDTO documentType;

    private ReferencedDocumentDTO referencedDocument;

    private DocumentClassificationDTO documentClassification;
}

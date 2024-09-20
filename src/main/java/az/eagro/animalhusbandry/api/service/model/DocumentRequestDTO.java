package az.eagro.animalhusbandry.api.service.model;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DocumentRequestDTO {

    private String documentTypeLabel;
    private String referencedDocumentTypeLabel;

}

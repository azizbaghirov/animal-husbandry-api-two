package az.eagro.animalhusbandry.api.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LegalPersonDTO {

    private String name;
    private String taxPayerIdentificationNumber;
    private boolean hasStamp;
    private boolean legal;

}

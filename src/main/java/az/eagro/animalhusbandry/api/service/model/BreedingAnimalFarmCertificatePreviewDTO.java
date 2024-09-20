package az.eagro.animalhusbandry.api.service.model;

import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BreedingAnimalFarmCertificatePreviewDTO {

    private UUID id;
    private String registrationNumber;
    private LocalDate certificationDate;
    private UUID excerptFileId;

}

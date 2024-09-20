package az.eagro.animalhusbandry.api.service.model;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhysicalPersonDTO {

    private UUID id;
    private String name;
    private String surname;
    private String patronymic;
    private String personalIdentificationNumber;

}

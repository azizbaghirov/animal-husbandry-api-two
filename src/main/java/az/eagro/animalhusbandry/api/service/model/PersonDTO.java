package az.eagro.animalhusbandry.api.service.model;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class PersonDTO {

    private UUID userId;
    private String name;
    private String surname;
    private String patronymic;
    private String pin;
    private LegalPersonDTO legalPerson;
    private List<RegionBasedRole> roles;
    private List<RegionDTO> regions;

}

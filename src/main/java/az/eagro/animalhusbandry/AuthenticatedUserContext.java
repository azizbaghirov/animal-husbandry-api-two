package az.eagro.animalhusbandry;

import az.eagro.animalhusbandry.api.service.model.LegalPersonDTO;
import az.eagro.animalhusbandry.api.service.model.RegionBasedRole;
import az.eagro.animalhusbandry.api.service.model.RegionDTO;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthenticatedUserContext {

    private UUID userId;
    private String name;
    private String surname;
    private String patronymic;
    private String pin;
    private LegalPersonDTO legalPerson;
    private List<RegionBasedRole> roles;
    private List<RegionDTO> regions;

}

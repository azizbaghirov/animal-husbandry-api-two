package az.eagro.animalhusbandry.api.service.model;

import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperatorDTO {

    private UUID id;
    private String personalIdentificationNumber;
    private String name;
    private String surname;
    private String patronymic;
    private List<GrantedAuthorityDTO> grantedAuthorities;
    private List<RegionDTO> regions;

}

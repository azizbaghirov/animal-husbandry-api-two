package az.eagro.animalhusbandry.api.service.model.certification;

import az.eagro.animalhusbandry.api.service.model.RegionBasedRole;
import az.eagro.animalhusbandry.model.UserRole;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificationCommissionMemberDTO {

    private UUID id;
    private UUID authorId;
    private String name;
    private String surname;
    private String patronymic;
    private Set<UserRole> role;

}

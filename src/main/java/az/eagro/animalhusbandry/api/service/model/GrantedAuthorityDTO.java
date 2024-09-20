package az.eagro.animalhusbandry.api.service.model;

import az.eagro.animalhusbandry.model.UserRole;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrantedAuthorityDTO {

    private UUID id;
    private UserRole role;

}

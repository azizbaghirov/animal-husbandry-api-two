package az.eagro.animalhusbandry.api.service.model;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RegionBasedRole {

    private String roleLabel;
    private List<RegionDTO> regions;

}

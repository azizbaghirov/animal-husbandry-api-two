package az.eagro.animalhusbandry.api.service.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AssetInputConfigurationDTO {

    private Integer id;
    private String name;
    private boolean mandatory;
    private String description;
    private Integer assetId;

}

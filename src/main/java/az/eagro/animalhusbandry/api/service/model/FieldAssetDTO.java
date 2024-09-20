package az.eagro.animalhusbandry.api.service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldAssetDTO {

    private Integer id;
    private String name;
    private boolean mandatory;
    private String description;
    private Integer assetId;

}

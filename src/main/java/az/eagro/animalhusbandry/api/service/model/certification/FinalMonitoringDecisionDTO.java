package az.eagro.animalhusbandry.api.service.model.certification;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinalMonitoringDecisionDTO {

    private UUID id;
    private Integer certificationApplicationId;
    private Boolean certified;
    private String justification;

}

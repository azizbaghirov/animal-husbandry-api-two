package az.eagro.animalhusbandry.api.service.model;

import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringDecisionState;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FinalMonitoringDecisionStateDTO {

    private FinalMonitoringDecisionState state;

}

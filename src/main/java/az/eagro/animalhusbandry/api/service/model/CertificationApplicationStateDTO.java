package az.eagro.animalhusbandry.api.service.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CertificationApplicationStateDTO {

    private boolean certificationApplicationRegistered;
    private boolean initialMonitoringDecisionRegistered;
    private boolean initialMonitoringDecisionNotCompliant;
    private boolean finalMonitoringJudgementsCollected;
    private boolean existsFinalMonitoringJudgement;
    private boolean certificationDenied;
    private boolean certified;

}

package az.eagro.animalhusbandry.api.service.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FinalMonitoringCertificationApplicationStateDTO {

    private CertificationApplicationStateDTO certificationApplicationState;
    private FinalMonitoringDecisionStateDTO finalMonitoringDecisionState;
    private OperatorsJudgementStateDTO operatorsJudgementState;

}

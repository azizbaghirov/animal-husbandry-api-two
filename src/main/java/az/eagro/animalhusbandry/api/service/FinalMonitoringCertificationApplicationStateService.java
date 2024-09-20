package az.eagro.animalhusbandry.api.service;

import az.eagro.animalhusbandry.api.service.model.CertificationApplicationStateDTO;
import az.eagro.animalhusbandry.api.service.model.FinalMonitoringCertificationApplicationStateDTO;
import az.eagro.animalhusbandry.api.service.model.FinalMonitoringDecisionStateDTO;
import az.eagro.animalhusbandry.api.service.model.OperatorsJudgementStateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FinalMonitoringCertificationApplicationStateService {

    private final FinalMonitoringDecisionService finalMonitoringDecisionService;
    private final CertificationApplicationService certificationApplicationService;
    private final FinalMonitoringJudgementService finalMonitoringJudgementService;


    public FinalMonitoringCertificationApplicationStateDTO getFinalMonitoringCertificationApplicationState(Integer applicationId) {

        FinalMonitoringDecisionStateDTO finalMonitoringDecisionState = finalMonitoringDecisionService.getFinalMonitoringDecisionState(applicationId);
        CertificationApplicationStateDTO applicationState = certificationApplicationService.getCertificationApplicationState(applicationId);
        OperatorsJudgementStateDTO operatorsJudgementState = finalMonitoringJudgementService.existsOwnJudgement(applicationId);
        return FinalMonitoringCertificationApplicationStateDTO
                .builder()
                .finalMonitoringDecisionState(finalMonitoringDecisionState)
                .certificationApplicationState(applicationState)
                .operatorsJudgementState(operatorsJudgementState)
                .build();
    }

}


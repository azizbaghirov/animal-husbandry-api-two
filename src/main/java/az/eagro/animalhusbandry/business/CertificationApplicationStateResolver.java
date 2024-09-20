package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.api.service.model.CertificationApplicationStateDTO;
import az.eagro.animalhusbandry.business.monitoring.FinalMonitoringDecisionMakingHelper;
import az.eagro.animalhusbandry.business.monitoring.FinalMonitoringJudgementManager;
import az.eagro.animalhusbandry.model.ApplicationStatus;
import az.eagro.animalhusbandry.model.CertificationApplicationEntity;
import az.eagro.animalhusbandry.model.FinalMonitoringStatus;
import az.eagro.animalhusbandry.model.InitialMonitoringStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class CertificationApplicationStateResolver {

    private final CertificationApplicationManager certificationApplicationManager;
    private final FinalMonitoringDecisionMakingHelper finalMonitoringDecisionMakingHelper;
    private final FinalMonitoringJudgementManager finalMonitoringJudgementManager;

    public CertificationApplicationStateDTO resolve(Integer applicationId) {

        CertificationApplicationEntity certificationApplication = certificationApplicationManager.getById(applicationId);

        CertificationApplicationStateDTO.CertificationApplicationStateDTOBuilder builder = CertificationApplicationStateDTO.builder();

        if (certificationApplication.getApplicationStatus().equals(ApplicationStatus.CERTIFIED)) {
            builder.certified(true);
        }

        if (certificationApplication.getApplicationStatus().equals(ApplicationStatus.REJECTED)
                && certificationApplication.getFinalMonitoringStatus().equals(FinalMonitoringStatus.NOT_COMPLIANT)) {
            builder.certificationDenied(true);
        }

        if (!certificationApplication.getInitialMonitoringStatus().equals(InitialMonitoringStatus.UNDEFINED)) {
            builder.initialMonitoringDecisionRegistered(true);
        }

        if (certificationApplication.getApplicationStatus().equals(ApplicationStatus.REGISTERED)) {
            builder.certificationApplicationRegistered(true);
        }

        if (finalMonitoringDecisionMakingHelper.isBreedingAnimalFarmCertifiable(applicationId)) {
            builder.finalMonitoringJudgementsCollected(true);
        }

        if (certificationApplication.getInitialMonitoringStatus().equals(InitialMonitoringStatus.NOT_COMPLIANT)) {
            builder.initialMonitoringDecisionNotCompliant(true);
        }
        builder.existsFinalMonitoringJudgement(finalMonitoringJudgementManager.existsJudgementByApplicationId(applicationId));

        return builder.build();
    }

}

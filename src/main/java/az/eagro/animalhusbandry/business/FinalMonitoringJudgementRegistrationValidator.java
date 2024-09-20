package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.business.monitoring.validation.FinalMonitoringDecisionRegistrationValidator;
import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringJudgementEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FinalMonitoringJudgementRegistrationValidator {

    private final InitialMonitoringDecisionValidation initialMonitoringDecisionValidation;
    private final FinalMonitoringJudgementValidation judgementValidation;
    private final FinalMonitoringDecisionRegistrationValidator decisionRegistrationValidator;
    private final AuthenticatedUserInfoProvider authenticatedUserInfoProvider;

    public void validate(FinalMonitoringJudgementEntity finalMonitoringJudgement) {
        Integer applicationId = finalMonitoringJudgement.getCertificationApplicationId();

        ensureAuthenticatedUserIsCommissionChairmanOrMember();
        initialMonitoringDecisionValidation.isCompliantCertificationApplication(applicationId);
        decisionRegistrationValidator.ensureFinalMonitoringDecisionNotRegistered(applicationId);
        judgementValidation.ensureNotExistsByApplicationIdFinalMonitoringJudgement(applicationId);
    }

    private void ensureAuthenticatedUserIsCommissionChairmanOrMember() {
        if (!authenticatedUserInfoProvider.isCommissionChairmanOrMemberOrSupervisor()) {
            throw new BusinessException("Unauthorized access");
        }
    }

}

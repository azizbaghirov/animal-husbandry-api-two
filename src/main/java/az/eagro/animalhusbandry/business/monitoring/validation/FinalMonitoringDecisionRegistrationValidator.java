package az.eagro.animalhusbandry.business.monitoring.validation;

import az.eagro.animalhusbandry.business.AuthenticatedUserInfoProvider;
import az.eagro.animalhusbandry.business.BusinessException;
import az.eagro.animalhusbandry.business.CertificationApplicationManager;
import az.eagro.animalhusbandry.business.monitoring.FinalMonitoringDecisionStateResolver;
import az.eagro.animalhusbandry.model.CertificationApplicationEntity;
import az.eagro.animalhusbandry.model.FinalMonitoringStatus;
import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringDecisionEntity;
import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringDecisionState;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FinalMonitoringDecisionRegistrationValidator {

    private final CertificationApplicationManager certificationApplicationManager;
    private final FinalMonitoringDecisionStateResolver finalMonitoringDecisionStateResolver;
    private final AuthenticatedUserInfoProvider authenticatedUserInfoProvider;

    public void validate(FinalMonitoringDecisionEntity finalMonitoringDecision) {
        Integer certificationApplicationId = finalMonitoringDecision.getCertificationApplicationId();
        CertificationApplicationEntity certificationApplication = certificationApplicationManager.getById(certificationApplicationId);

        ensureAuthenticatedUserIsCommissionChairman();
        ensureDecisionRegistrationPermitted(certificationApplication);
    }

    public void ensureFinalMonitoringDecisionNotRegistered(Integer applicationId) {
        FinalMonitoringDecisionState decisionState = finalMonitoringDecisionStateResolver.resolve(applicationId);

        if (decisionState == FinalMonitoringDecisionState.REGISTERED) {
            throw new BusinessException("A final monitoring decision is already registered.");
        }
    }

    private void ensureAuthenticatedUserIsCommissionChairman() {
        if (!authenticatedUserInfoProvider.isCommissionChairman()) {
            throw new BusinessException("Unauthorized access");
        }
    }

    private void ensureDecisionRegistrationPermitted(CertificationApplicationEntity certificationApplication) {
        Integer applicationId = certificationApplication.getId();
        FinalMonitoringDecisionState decisionState = finalMonitoringDecisionStateResolver.resolve(applicationId);

        switch (decisionState) {
            case REGISTERED -> throw new BusinessException("A final monitoring decision is already registered");
            case REGISTRATION_PROHIBITED -> throw new BusinessException("Operation forbidden");
            default -> {
            }
        }
    }

}

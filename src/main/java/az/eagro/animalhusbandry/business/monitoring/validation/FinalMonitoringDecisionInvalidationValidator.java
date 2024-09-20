package az.eagro.animalhusbandry.business.monitoring.validation;

import az.eagro.animalhusbandry.business.CertificationApplicationManager;
import az.eagro.animalhusbandry.business.monitoring.FinalMonitoringDecisionStateResolver;
import az.eagro.animalhusbandry.model.CertificationApplicationEntity;
import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringDecisionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FinalMonitoringDecisionInvalidationValidator {

    private final CertificationApplicationManager certificationApplicationManager;
    private final FinalMonitoringDecisionStateResolver finalMonitoringDecisionStateResolver;

    public void validate(FinalMonitoringDecisionEntity finalMonitoringDecision) {
        // todo: validate user role

        CertificationApplicationEntity certificationApplication =
                certificationApplicationManager.getById(finalMonitoringDecision.getCertificationApplicationId());

        // validateDecisionState(certificationApplication);

        // todo: apply time limitation
    }
}

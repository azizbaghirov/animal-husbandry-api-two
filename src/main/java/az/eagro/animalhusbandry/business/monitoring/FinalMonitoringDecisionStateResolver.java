package az.eagro.animalhusbandry.business.monitoring;

import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringDecisionEntity;
import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringDecisionState;
import az.eagro.animalhusbandry.repository.monitoring.FinalMonitoringDecisionRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FinalMonitoringDecisionStateResolver {

    private final FinalMonitoringDecisionMakingHelper decisionMakingHelper;
    private final FinalMonitoringDecisionRepository finalMonitoringDecisionRepository;

    public FinalMonitoringDecisionState resolve(Integer certificationApplicationId) {
        Optional<FinalMonitoringDecisionEntity> decisionRef =
                finalMonitoringDecisionRepository.findByCertificationApplicationId(certificationApplicationId);

        if (decisionRef.isPresent()) {
            return FinalMonitoringDecisionState.REGISTERED;
        }

        if (!decisionMakingHelper.isFinalMonitoringDecisionRegistrationPermitted(certificationApplicationId)) {
            return FinalMonitoringDecisionState.REGISTRATION_PROHIBITED;
        }

        return decisionMakingHelper.isBreedingAnimalFarmCertifiable(certificationApplicationId)
                ? FinalMonitoringDecisionState.CERTIFICATION_PERMITTED
                : FinalMonitoringDecisionState.CERTIFICATION_DENIED;
    }
}

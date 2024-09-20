package az.eagro.animalhusbandry.business.monitoring.validation;

import az.eagro.animalhusbandry.business.BusinessException;
import az.eagro.animalhusbandry.business.CertificationApplicationAccessValidator;
import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringDecisionEntity;
import az.eagro.animalhusbandry.repository.monitoring.FinalMonitoringDecisionRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FinalMonitoringDecisionAccessValidator {

    private final FinalMonitoringDecisionRepository finalMonitoringDecisionRepository;
    private final CertificationApplicationAccessValidator certificationApplicationAccessValidator;

    public void validateById(UUID finalMonitoringDecisionId) {
        FinalMonitoringDecisionEntity finalMonitoringDecision = finalMonitoringDecisionRepository.findById(finalMonitoringDecisionId)
                .orElseThrow(() -> new BusinessException("Final monitoring decision not found"));

        validateByCertificationApplicationId(finalMonitoringDecision.getCertificationApplicationId());
    }

    public void validateByCertificationApplicationId(Integer certificationApplicationId) {
        certificationApplicationAccessValidator.validateAccess(certificationApplicationId);
    }

}

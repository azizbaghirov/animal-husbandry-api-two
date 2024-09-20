package az.eagro.animalhusbandry.business.monitoring;

import az.eagro.animalhusbandry.business.BusinessException;
import az.eagro.animalhusbandry.business.CertificationApplicationAccessValidator;
import az.eagro.animalhusbandry.business.CertificationApplicationManager;
import az.eagro.animalhusbandry.business.monitoring.validation.FinalMonitoringDecisionAccessValidator;
import az.eagro.animalhusbandry.business.monitoring.validation.FinalMonitoringDecisionRegistrationValidator;
import az.eagro.animalhusbandry.model.CertificationApplicationEntity;
import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringDecisionEntity;
import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringDecisionState;
import az.eagro.animalhusbandry.repository.monitoring.FinalMonitoringDecisionRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FinalMonitoringDecisionManager {

    private final FinalMonitoringDecisionRepository finalMonitoringDecisionRepository;
    private final FinalMonitoringDecisionStateResolver finalMonitoringDecisionStateResolver;
    private final FinalMonitoringDecisionAccessValidator finalMonitoringDecisionAccessValidator;
    private final CertificationApplicationAccessValidator certificationApplicationAccessValidator;

    public Optional<FinalMonitoringDecisionEntity> findById(UUID finalMonitoringDecisionId) {
        finalMonitoringDecisionAccessValidator.validateById(finalMonitoringDecisionId);

        return finalMonitoringDecisionRepository.findById(finalMonitoringDecisionId);
    }

    public Optional<FinalMonitoringDecisionEntity> findByCertificationApplicationId(Integer certificationApplicationId) {
        finalMonitoringDecisionAccessValidator.validateByCertificationApplicationId(certificationApplicationId);

        return finalMonitoringDecisionRepository.findByCertificationApplicationId(certificationApplicationId);
    }

    public FinalMonitoringDecisionEntity getById(UUID finalMonitoringDecisionId) {
        return findById(finalMonitoringDecisionId)
                .orElseThrow(() -> new BusinessException("Final monitoring decision not found"));
    }

    public FinalMonitoringDecisionState getFinalMonitoringDecisionState(Integer certificationApplicationId) {
        certificationApplicationAccessValidator.validateAccess(certificationApplicationId);

        return finalMonitoringDecisionStateResolver.resolve(certificationApplicationId);
    }




}

package az.eagro.animalhusbandry.business.monitoring;

import az.eagro.animalhusbandry.business.AuthenticatedUserInfoProvider;
import az.eagro.animalhusbandry.business.BusinessException;
import az.eagro.animalhusbandry.business.monitoring.validation.FinalMonitoringDecisionRegistrationValidator;
import az.eagro.animalhusbandry.model.OperatorEntity;
import az.eagro.animalhusbandry.model.event.CertificationApplicationCertifiedEvent;
import az.eagro.animalhusbandry.model.event.CertificationApplicationRejectedEvent;
import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringDecisionEntity;
import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringDecisionState;
import az.eagro.animalhusbandry.repository.monitoring.FinalMonitoringDecisionRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FinalMonitoringDecisionRegistrationManager {

    private final FinalMonitoringDecisionRepository finalMonitoringDecisionRepository;
    private final FinalMonitoringDecisionRegistrationValidator finalMonitoringDecisionRegistrationValidator;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final FinalMonitoringDecisionStateResolver finalMonitoringDecisionStateResolver;
    private final AuthenticatedUserInfoProvider authenticatedUserInfoProvider;

    @Transactional
    public FinalMonitoringDecisionEntity createPermittedDecision(FinalMonitoringDecisionEntity finalDecision) {
        finalMonitoringDecisionRegistrationValidator.validate(finalDecision);
        FinalMonitoringDecisionState decisionState = finalMonitoringDecisionStateResolver.resolve(finalDecision.getCertificationApplicationId());

        if (!decisionState.equals(FinalMonitoringDecisionState.CERTIFICATION_PERMITTED)) {
            throw new BusinessException("Final monitoring decision state is not permitted");
        }

        finalDecision.setCreatedBy(OperatorEntity.builder()
                .id(authenticatedUserInfoProvider.getUserId())
                .name(authenticatedUserInfoProvider.getUserName())
                .surname(authenticatedUserInfoProvider.getUserSurname())
                .patronymic(authenticatedUserInfoProvider.getUserPatronymic())
                .build());

        finalDecision.setCertified(true);

        FinalMonitoringDecisionEntity newFinalMonitoringDecision = finalMonitoringDecisionRepository.save(finalDecision);

        applicationEventPublisher.publishEvent(new CertificationApplicationCertifiedEvent(this, newFinalMonitoringDecision));

        return newFinalMonitoringDecision;
    }


    @Transactional
    public FinalMonitoringDecisionEntity createDeniedDecision(FinalMonitoringDecisionEntity finalDecision) {
        finalMonitoringDecisionRegistrationValidator.validate(finalDecision);
        FinalMonitoringDecisionState decisionState = finalMonitoringDecisionStateResolver.resolve(finalDecision.getCertificationApplicationId());

        if (!decisionState.equals(FinalMonitoringDecisionState.CERTIFICATION_DENIED)) {
            throw new BusinessException("Final monitoring decision state is not denied");
        }

        finalDecision.setCertified(false);
        finalDecision.setCreatedBy(OperatorEntity.builder().id(authenticatedUserInfoProvider.getUserId()).build());

        FinalMonitoringDecisionEntity newDecision = finalMonitoringDecisionRepository.save(finalDecision);

        applicationEventPublisher.publishEvent(new CertificationApplicationRejectedEvent(this, newDecision.getCertificationApplicationId()));

        return newDecision;
    }
}
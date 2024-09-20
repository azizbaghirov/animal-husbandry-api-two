package az.eagro.animalhusbandry.business.monitoring;

import az.eagro.animalhusbandry.business.AuthenticatedUserInfoProvider;
import az.eagro.animalhusbandry.model.OperatorEntity;
import az.eagro.animalhusbandry.model.event.FinalMonitoringDecisionCanceledEvent;
import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringDecisionEntity;
import az.eagro.animalhusbandry.repository.monitoring.FinalMonitoringDecisionRepository;
import az.eagro.animalhusbandry.util.timeutil.TimeValidation;
import java.time.Instant;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FinalMonitoringDecisionCancellationManager {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final AuthenticatedUserInfoProvider authenticatedUserInfoProvider;
    private final FinalMonitoringDecisionManager finalMonitoringDecisionManager;
    private final FinalMonitoringDecisionRepository finalMonitoringDecisionRepository;

    @Transactional
    public void deletedById(UUID finalMonitoringDecisionId) {
        FinalMonitoringDecisionEntity finalMonitoringDecision = finalMonitoringDecisionManager.getById(finalMonitoringDecisionId);

        TimeValidation.validate(finalMonitoringDecision.getCreatedAt());
        finalMonitoringDecision.setInvalidatedBy(OperatorEntity.builder().id(authenticatedUserInfoProvider.getUserId()).build());
        finalMonitoringDecision.setInvalidatedAt(Instant.now());
        FinalMonitoringDecisionEntity finalDecision = finalMonitoringDecisionRepository.save(finalMonitoringDecision);

        applicationEventPublisher.publishEvent(new FinalMonitoringDecisionCanceledEvent(this, finalDecision));
    }

    public boolean hasPermitCancellationFinalMonitoringDecision(FinalMonitoringDecisionEntity finalMonitoringDecision) {
        return TimeValidation.twentyFourHoursPassed(finalMonitoringDecision.getCreatedAt())
                && authenticatedUserInfoProvider.isCommissionChairman();
    }
}

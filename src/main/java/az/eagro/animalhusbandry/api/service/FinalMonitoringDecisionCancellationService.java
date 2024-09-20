package az.eagro.animalhusbandry.api.service;

import az.eagro.animalhusbandry.business.monitoring.FinalMonitoringDecisionCancellationManager;
import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringDecisionEntity;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FinalMonitoringDecisionCancellationService {

    private final FinalMonitoringDecisionCancellationManager finalMonitoringDecisionCancellationManager;

    public void deletedById(UUID finalMonitoringDecisionId) {
        finalMonitoringDecisionCancellationManager.deletedById(finalMonitoringDecisionId);
    }

    public boolean hasPermitCancellationFinalMonitoringDecision(FinalMonitoringDecisionEntity finalMonitoringDecision) {
        return finalMonitoringDecisionCancellationManager.hasPermitCancellationFinalMonitoringDecision(finalMonitoringDecision);
    }
}

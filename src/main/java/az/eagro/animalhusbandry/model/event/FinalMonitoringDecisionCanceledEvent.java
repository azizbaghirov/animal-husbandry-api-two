package az.eagro.animalhusbandry.model.event;

import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringDecisionEntity;
import org.springframework.context.ApplicationEvent;

public final class FinalMonitoringDecisionCanceledEvent extends ApplicationEvent {

    private final FinalMonitoringDecisionEntity finalMonitoringDecision;

    public FinalMonitoringDecisionCanceledEvent(Object source, FinalMonitoringDecisionEntity finalMonitoringDecision) {
        super(source);
        this.finalMonitoringDecision = finalMonitoringDecision;
    }

    public FinalMonitoringDecisionEntity getFinalMonitoringDecision() {
        return finalMonitoringDecision;
    }
}

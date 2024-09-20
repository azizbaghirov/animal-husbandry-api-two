package az.eagro.animalhusbandry.model.event;

import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringDecisionEntity;
import org.springframework.context.ApplicationEvent;

public final class CertificationApplicationCertifiedEvent extends ApplicationEvent {

    private final FinalMonitoringDecisionEntity finalMonitoringDecision;

    public CertificationApplicationCertifiedEvent(Object source, FinalMonitoringDecisionEntity finalMonitoringDecision) {
        super(source);
        this.finalMonitoringDecision = finalMonitoringDecision;
    }

    public FinalMonitoringDecisionEntity getFinalMonitoringDecision() {
        return finalMonitoringDecision;
    }
}

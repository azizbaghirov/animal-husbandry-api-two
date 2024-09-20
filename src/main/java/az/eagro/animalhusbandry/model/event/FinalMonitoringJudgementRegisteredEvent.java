package az.eagro.animalhusbandry.model.event;

import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringJudgementEntity;
import org.springframework.context.ApplicationEvent;

public final class FinalMonitoringJudgementRegisteredEvent extends ApplicationEvent {

    private final FinalMonitoringJudgementEntity finalMonitoringJudgement;

    public FinalMonitoringJudgementRegisteredEvent(Object source, FinalMonitoringJudgementEntity finalMonitoringJudgement) {
        super(source);
        this.finalMonitoringJudgement = finalMonitoringJudgement;
    }

    public FinalMonitoringJudgementEntity getFinalMonitoringJudgement() {
        return finalMonitoringJudgement;
    }
}

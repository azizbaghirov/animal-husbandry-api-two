package az.eagro.animalhusbandry.model.event;

import org.springframework.context.ApplicationEvent;

public final class CertificationApplicationRejectedEvent extends ApplicationEvent {

    private final Integer certificationApplicationId;

    public CertificationApplicationRejectedEvent(Object source, Integer certificationApplicationId) {
        super(source);
        this.certificationApplicationId = certificationApplicationId;
    }

    public Integer getCertificationApplicationId() {
        return certificationApplicationId;
    }
}

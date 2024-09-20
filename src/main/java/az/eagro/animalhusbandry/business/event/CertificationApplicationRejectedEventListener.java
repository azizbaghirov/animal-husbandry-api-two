package az.eagro.animalhusbandry.business.event;

import az.eagro.animalhusbandry.business.CertificationApplicationManager;
import az.eagro.animalhusbandry.model.ApplicationStatus;
import az.eagro.animalhusbandry.model.CertificationApplicationEntity;
import az.eagro.animalhusbandry.model.FinalMonitoringStatus;
import az.eagro.animalhusbandry.model.event.CertificationApplicationRejectedEvent;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class CertificationApplicationRejectedEventListener implements ApplicationListener<CertificationApplicationRejectedEvent> {

    private final CertificationApplicationManager certificationApplicationManager;

    @Override
    @Transactional(Transactional.TxType.MANDATORY)
    public void onApplicationEvent(CertificationApplicationRejectedEvent event) {
        Integer certificationApplicationId = event.getCertificationApplicationId();
        CertificationApplicationEntity application = certificationApplicationManager.getById(certificationApplicationId);

        application.setApplicationStatus(ApplicationStatus.REJECTED);
        application.getFarm().setDeletable(true);
        application.setFinalMonitoringStatus(FinalMonitoringStatus.NOT_COMPLIANT);

        certificationApplicationManager.update(application);
    }
}

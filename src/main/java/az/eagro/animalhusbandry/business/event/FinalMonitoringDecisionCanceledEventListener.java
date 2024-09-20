package az.eagro.animalhusbandry.business.event;

import az.eagro.animalhusbandry.business.CertificationApplicationManager;
import az.eagro.animalhusbandry.business.certification.BreedingAnimalFarmCertificateManager;
import az.eagro.animalhusbandry.model.ApplicationStatus;
import az.eagro.animalhusbandry.model.CertificationApplicationEntity;
import az.eagro.animalhusbandry.model.FinalMonitoringStatus;
import az.eagro.animalhusbandry.model.event.FinalMonitoringDecisionCanceledEvent;
import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringDecisionEntity;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FinalMonitoringDecisionCanceledEventListener implements ApplicationListener<FinalMonitoringDecisionCanceledEvent> {

    private final CertificationApplicationManager certificationApplicationManager;
    private final BreedingAnimalFarmCertificateManager certificateManager;

    @Override
    @Transactional(Transactional.TxType.MANDATORY)
    public void onApplicationEvent(FinalMonitoringDecisionCanceledEvent event) {
        invalidateCertificateIfPresent(event);
        rollbackCertificationApplicationStatuses(event);
    }

    private void invalidateCertificateIfPresent(FinalMonitoringDecisionCanceledEvent event) {
        FinalMonitoringDecisionEntity finalMonitoringDecision = event.getFinalMonitoringDecision();

        if (finalMonitoringDecision.getCertified()) {
            certificateManager.deleteCertificate(finalMonitoringDecision);
        }
    }

    private void rollbackCertificationApplicationStatuses(FinalMonitoringDecisionCanceledEvent event) {
        FinalMonitoringDecisionEntity finalMonitoringDecision = event.getFinalMonitoringDecision();
        Integer certificationApplicationId = finalMonitoringDecision.getCertificationApplicationId();
        CertificationApplicationEntity application = certificationApplicationManager.getById(certificationApplicationId);

        application.setApplicationStatus(ApplicationStatus.MONITORING_IN_PROGRESS);
        application.setFinalMonitoringStatus(FinalMonitoringStatus.UNDEFINED);

        certificationApplicationManager.update(application);
    }
}

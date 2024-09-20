package az.eagro.animalhusbandry.business.event;

import az.eagro.animalhusbandry.business.CertificationApplicationManager;
import az.eagro.animalhusbandry.business.certification.BreedingAnimalFarmCertificateManager;
import az.eagro.animalhusbandry.model.ApplicationStatus;
import az.eagro.animalhusbandry.model.CertificationApplicationEntity;
import az.eagro.animalhusbandry.model.FinalMonitoringStatus;
import az.eagro.animalhusbandry.model.event.CertificationApplicationCertifiedEvent;
import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringDecisionEntity;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class CertificationApplicationCertifiedEventListener implements ApplicationListener<CertificationApplicationCertifiedEvent> {

    private final CertificationApplicationManager certificationApplicationManager;
    private final BreedingAnimalFarmCertificateManager certificateManager;

    @Override
    @Transactional(Transactional.TxType.MANDATORY)
    public void onApplicationEvent(CertificationApplicationCertifiedEvent event) {
        updateCertificationStatuses(event);
        createCertificate(event);
    }

    private void updateCertificationStatuses(CertificationApplicationCertifiedEvent event) {
        FinalMonitoringDecisionEntity finalMonitoringDecision = event.getFinalMonitoringDecision();
        Integer certificationApplicationId = finalMonitoringDecision.getCertificationApplicationId();
        CertificationApplicationEntity application = certificationApplicationManager.getById(certificationApplicationId);

        application.setApplicationStatus(ApplicationStatus.CERTIFIED);
        application.setFinalMonitoringStatus(FinalMonitoringStatus.COMPLIANT);

        certificationApplicationManager.update(application);
    }

    @SneakyThrows
    private void createCertificate(CertificationApplicationCertifiedEvent event) {
        FinalMonitoringDecisionEntity decision = event.getFinalMonitoringDecision();

        certificateManager.createCertificate(decision);
    }
}

package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.model.ApplicationStatus;
import az.eagro.animalhusbandry.model.CertificationApplicationEntity;
import az.eagro.animalhusbandry.model.InitialMonitoringDataEntity;
import az.eagro.animalhusbandry.model.InitialMonitoringStatus;
import az.eagro.animalhusbandry.model.OperatorEntity;
import az.eagro.animalhusbandry.repository.InitialMonitoringDataRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class InitialMonitoringDataManager {

    private final InitialMonitoringDataRepository monitoringDataRepository;
    private final AuthenticatedUserInfoProvider auth;
    private final CertificationApplicationManager certificationApplicationManager;
    private final VaccinationValidator vaccinationValidator;
    private final InitialMonitoringDataValidation initialMonitoringDataValidation;

    @Transactional
    public void createInitialMonitoringData(InitialMonitoringDataEntity newInitialMonitoringData) {

        newInitialMonitoringData.getMonitoringDecision().setCreatedBy(OperatorEntity.builder().id(auth.getUserId()).build());
        CertificationApplicationEntity application = certificationApplicationManager.findRegisteredApplicationById(
                newInitialMonitoringData.getMonitoringDecision().getApplication().getId());
        if (newInitialMonitoringData.getMonitoringDecision().isCompliant()) {
            application.setApplicationStatus(ApplicationStatus.MONITORING_IN_PROGRESS);
            application.setInitialMonitoringStatus(InitialMonitoringStatus.COMPLIANT);
        } else {
            application.setApplicationStatus(ApplicationStatus.REJECTED);
            application.getFarm().setDeletable(true);
            application.setInitialMonitoringStatus(InitialMonitoringStatus.NOT_COMPLIANT);
        }
        newInitialMonitoringData.getMonitoringDecision().setApplication(application);

        initialMonitoringDataValidation.checkCertificationApplicationData(newInitialMonitoringData);
        vaccinationValidator.ensureHasVaccinations(newInitialMonitoringData);
        monitoringDataRepository.save(newInitialMonitoringData);

    }

    public InitialMonitoringDataEntity getByDecisionId(Integer decisionId) {
        return monitoringDataRepository.findByDecisionId(decisionId).orElseThrow(
                () -> new BusinessException("Damazlıq heyvandarlıq təsərrüfatının ilkin monitorinqinin nəticəsi qeydə alınmayıb. "));
    }

}

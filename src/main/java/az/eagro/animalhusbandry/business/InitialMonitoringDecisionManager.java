package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.model.ApplicationStatus;
import az.eagro.animalhusbandry.model.FarmerAccountEntity;
import az.eagro.animalhusbandry.model.InitialMonitoringDecisionEntity;
import az.eagro.animalhusbandry.model.InitialMonitoringStatus;
import az.eagro.animalhusbandry.model.OperatorEntity;
import az.eagro.animalhusbandry.repository.InitialMonitoringDecisionRepository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class InitialMonitoringDecisionManager {

    private final AuthenticatedUserInfoProvider userInfoProvider;
    private final FarmerAccountManager farmerAccount;
    private final InitialMonitoringDecisionRepository initialMonitoringDecisionRepository;
    private final CertificationApplicationAccessValidator accessValidator;
    private final FinalMonitoringJudgementValidation judgementValidation;

    public Optional<InitialMonitoringDecisionEntity> getByApplicationIdForFarmer(Integer applicationId) {
        var farmer = farmerAccount.findFarmerAccount();
        return getApplicationByIdOtFarmerIdOrRegionIds(applicationId, farmer, null);
    }

    public Optional<InitialMonitoringDecisionEntity> getByApplicationIdForInspector(Integer applicationId) {
        List<Integer> regionIds = userInfoProvider.getRegions().isEmpty() ? null : userInfoProvider.getRegionIds();
        return getApplicationByIdOtFarmerIdOrRegionIds(applicationId, null, regionIds);
    }

    public Optional<InitialMonitoringDecisionEntity> getByApplicationIdForCommission(Integer applicationId) {
        return getApplicationByIdOtFarmerIdOrRegionIds(applicationId, null, null);
    }

    private Optional<InitialMonitoringDecisionEntity> getApplicationByIdOtFarmerIdOrRegionIds(
            Integer id, FarmerAccountEntity farmer, List<Integer> regionIds) {
        return initialMonitoringDecisionRepository.findByApplicationIdOrFarmerIdOrRegionIds(id, farmer, regionIds);
    }

    @Transactional
    public void deleteById(Integer initialMonitoringDecisionId) {
        InitialMonitoringDecisionEntity initialMonitoringDecision = findInitialMonitoringDecision(initialMonitoringDecisionId);
        Integer applicationId = initialMonitoringDecision.getApplication().getId();

        accessValidator.validateAccess(applicationId);
        judgementValidation.ensureNotExistsByCertificationApplicationId(applicationId);

        initialMonitoringDecision.setDeletedBy(OperatorEntity.builder().id(userInfoProvider.getUserId()).build());
        initialMonitoringDecision.setDeletedAt(Instant.now());

        initialMonitoringDecision.getApplication().setApplicationStatus(ApplicationStatus.REGISTERED);
        initialMonitoringDecision.getApplication().setInitialMonitoringStatus(InitialMonitoringStatus.UNDEFINED);

        initialMonitoringDecisionRepository.save(initialMonitoringDecision);

    }
    
    private InitialMonitoringDecisionEntity findInitialMonitoringDecision(Integer initialMonitoringDecisionId) {
        InitialMonitoringDecisionEntity initialMonitoringDecision = initialMonitoringDecisionRepository.findById(initialMonitoringDecisionId)
                .orElseThrow(() -> new BusinessException("Initial monitoring decision not found."));
        return initialMonitoringDecision;
    }
}

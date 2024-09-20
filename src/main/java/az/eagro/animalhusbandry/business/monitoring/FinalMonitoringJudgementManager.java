package az.eagro.animalhusbandry.business.monitoring;

import az.eagro.animalhusbandry.api.service.model.OperatorsJudgementStateDTO;
import az.eagro.animalhusbandry.business.AuthenticatedUserInfoProvider;
import az.eagro.animalhusbandry.business.BusinessException;
import az.eagro.animalhusbandry.business.CertificationApplicationAccessValidator;
import az.eagro.animalhusbandry.business.CertificationApplicationManager;
import az.eagro.animalhusbandry.business.FinalMonitoringJudgementRegistrationValidator;
import az.eagro.animalhusbandry.business.certification.CertificationCommissionMembershipManager;
import az.eagro.animalhusbandry.business.monitoring.validation.FinalMonitoringJudgementModificationAndDeletionValidator;
import az.eagro.animalhusbandry.model.CertificationApplicationEntity;
import az.eagro.animalhusbandry.model.OperatorEntity;
import az.eagro.animalhusbandry.model.certification.CertificationCommissionMemberEntity;
import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringJudgementEntity;
import az.eagro.animalhusbandry.repository.monitoring.FinalMonitoringJudgementRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FinalMonitoringJudgementManager {

    private final CertificationApplicationManager certificationApplicationManager;
    private final FinalMonitoringJudgementRepository judgementRepository;
    private final CertificationApplicationAccessValidator certificationAccessValidator;
    private final AuthenticatedUserInfoProvider userInfo;
    private final FinalMonitoringJudgementRegistrationValidator judgementRegistrationValidator;
    private final FinalMonitoringJudgementModificationAndDeletionValidator judgementModificationAndDeletionValidator;
    private final CertificationCommissionMembershipManager certificationCommissionMembershipManager;

    public List<FinalMonitoringJudgementEntity> findAllByCertificationApplication(Integer certificationApplicationId) {

        certificationAccessValidator.validateAccess(certificationApplicationId);
        CertificationApplicationEntity certificationApplication = certificationApplicationManager.getById(certificationApplicationId);

        return judgementRepository.findAllByCertificationApplicationId(certificationApplication.getId());
    }

    public FinalMonitoringJudgementEntity getById(UUID finalMonitoringJudgementId) {
        var finalMonitoringJudgement = judgementRepository.findById(finalMonitoringJudgementId).orElse(null);

        if (finalMonitoringJudgement != null) {
            certificationAccessValidator.validateAccess(finalMonitoringJudgement.getCertificationApplicationId());
        }

        return finalMonitoringJudgement;
    }

    public FinalMonitoringJudgementEntity create(FinalMonitoringJudgementEntity newJudgement) {
        judgementRegistrationValidator.validate(newJudgement);

        newJudgement.setCreatedBy(OperatorEntity.builder().id(userInfo.getUserId()).build());

        return judgementRepository.save(newJudgement);
    }

    public FinalMonitoringJudgementEntity update(FinalMonitoringJudgementEntity modifiedJudgement) {
        judgementModificationAndDeletionValidator.validate(modifiedJudgement);

        return judgementRepository.save(modifiedJudgement);
    }

    public void deleteById(UUID judgementId) {
        FinalMonitoringJudgementEntity judgement = FinalMonitoringJudgementEntity.builder().id(judgementId).build();

        judgementModificationAndDeletionValidator.validate(judgement);
        judgementRepository.delete(judgement);
    }

    public OperatorsJudgementStateDTO existsOwnJudgement(Integer applicationId) {
        UUID userId = userInfo.getUserId();

        List<CertificationCommissionMemberEntity> members = certificationCommissionMembershipManager.getCurrentCertificationCommissionMembers();
        List<FinalMonitoringJudgementEntity> judgements = findAllByCertificationApplication(applicationId);

        boolean commissionMember = members.stream()
                .anyMatch(member -> member.getOperator().getId().equals(userId));

        boolean hasJudgements = judgements.stream()
                .anyMatch(finalMonitoringJudgement -> finalMonitoringJudgement.getCreatedBy().getId().equals(userId));

        return OperatorsJudgementStateDTO.builder()
                .existsInMembers(commissionMember)
                .existsJudgement(hasJudgements)
                .build();
    }

    public boolean existsJudgementByApplicationId(Integer applicationId) {
        return judgementRepository.existsByCertificationApplicationId(applicationId);
    }

}


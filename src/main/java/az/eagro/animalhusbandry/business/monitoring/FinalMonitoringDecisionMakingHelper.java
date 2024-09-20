package az.eagro.animalhusbandry.business.monitoring;

import az.eagro.animalhusbandry.business.BusinessException;
import az.eagro.animalhusbandry.model.UserRole;
import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringJudgementEntity;
import az.eagro.animalhusbandry.repository.monitoring.FinalMonitoringJudgementRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FinalMonitoringDecisionMakingHelper {

    private static final Integer MIN_JUDGEMENT_COUNT = 3;

    private final FinalMonitoringJudgementRepository judgementRepository;

    public boolean isBreedingAnimalFarmCertifiable(Integer applicationId) {
        List<FinalMonitoringJudgementEntity> judgements = judgementRepository.findAllByCertificationApplicationId(applicationId);

        if (!isFinalMonitoringDecisionRegistrationPermitted(applicationId)) {
            return false;
        }

        long positiveJudgementsCount = judgements.stream()
                .map(FinalMonitoringJudgementEntity::getCompliant)
                .filter(Boolean.TRUE::equals)
                .count();

        long negativeJudgementCount = judgements.stream()
                .map(FinalMonitoringJudgementEntity::getCompliant)
                .filter(Boolean.FALSE::equals)
                .count();

        if (positiveJudgementsCount == negativeJudgementCount) {
            return findCommissionChairmansJudgement(judgements)
                    .map(FinalMonitoringJudgementEntity::getCompliant)
                    .orElseThrow(() -> new BusinessException("Commission chairman's judgement is missing"));
        }

        return positiveJudgementsCount > negativeJudgementCount;
    }

    public boolean isFinalMonitoringDecisionRegistrationPermitted(Integer applicationId) {
        List<FinalMonitoringJudgementEntity> judgements = judgementRepository.findAllByCertificationApplicationId(applicationId);

        if (judgements.size() < MIN_JUDGEMENT_COUNT) {
            return false;
        }

        return findCommissionChairmansJudgement(judgements)
                .isPresent();
    }

    public Optional<FinalMonitoringJudgementEntity> findCommissionChairmansJudgement(
            List<FinalMonitoringJudgementEntity> judgements) {
        return judgements.stream()
                .filter(this::isJudgementCreatedByComissionChairman)
                .findFirst();
    }

    public boolean isJudgementCreatedByComissionChairman(FinalMonitoringJudgementEntity judgement) {
        return judgement.getCreatedBy()
                .getGrantedAuthorities()
                .stream()
                .anyMatch(operatorRole -> operatorRole.getRole() == UserRole.COMMISSION_CHAIRMAN);
    }

}

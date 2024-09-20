package az.eagro.animalhusbandry.business.monitoring.validation;

import az.eagro.animalhusbandry.business.AuthenticatedUserInfoProvider;
import az.eagro.animalhusbandry.business.BusinessException;
import az.eagro.animalhusbandry.business.monitoring.FinalMonitoringDecisionManager;
import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringJudgementEntity;
import az.eagro.animalhusbandry.repository.monitoring.FinalMonitoringJudgementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FinalMonitoringJudgementModificationAndDeletionValidator {

    private final FinalMonitoringJudgementRepository judgementRepository;
    private final FinalMonitoringDecisionManager finalMonitoringDecisionManager;
    private final AuthenticatedUserInfoProvider authenticatedUserInfoProvider;

    public void validate(FinalMonitoringJudgementEntity finalMonitoringJudgement) {
        FinalMonitoringJudgementEntity judgement = judgementRepository.findById(finalMonitoringJudgement.getId())
                .orElseThrow(() -> new BusinessException("Final Monitoring Judgement not found. "));

        ensureJudgementCreatedByAuthenticatedUser(judgement);
        ensureFinalMonitoringDecisionNotRegistered(judgement);
    }

    public void ensureJudgementCreatedByAuthenticatedUser(FinalMonitoringJudgementEntity finalMonitoringJudgement) {
        if (!finalMonitoringJudgement.getCreatedBy().getId().equals(authenticatedUserInfoProvider.getUserId())) {
            throw new BusinessException("Unauthorized access to judgement. ");
        }
    }

    public void ensureFinalMonitoringDecisionNotRegistered(FinalMonitoringJudgementEntity judgement) {
        Integer certificationApplicationId = judgement.getCertificationApplicationId();

        finalMonitoringDecisionManager.findByCertificationApplicationId(certificationApplicationId)
                .ifPresent(decision -> {
                    throw new BusinessException("Operation forbidden.");
                });
    }

}

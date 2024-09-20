package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.repository.monitoring.FinalMonitoringJudgementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FinalMonitoringJudgementValidation {

    private final FinalMonitoringJudgementRepository judgementRepository;
    private final AuthenticatedUserInfoProvider userInfo;

    public void ensureNotExistsByApplicationIdFinalMonitoringJudgement(Integer applicationId) {
        if (judgementRepository.existsByCertificationApplicationIdAndCreatedById(applicationId, userInfo.getUserId())) {
            throw new BusinessException("İstifadəçinin rəyi artıq mövcuddur. ");
        }
    }

    public void ensureNotExistsByCertificationApplicationId(Integer applicationId) {
        if (judgementRepository.existsByCertificationApplicationId(applicationId)) {
            throw new BusinessException("Exists final monitoring judgement.");
        }
    }

}

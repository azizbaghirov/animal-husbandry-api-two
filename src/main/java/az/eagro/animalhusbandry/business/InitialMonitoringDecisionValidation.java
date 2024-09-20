package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.repository.InitialMonitoringDecisionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class InitialMonitoringDecisionValidation {

    private final InitialMonitoringDecisionRepository decisionRepository;

    public void isCompliantCertificationApplication(Integer applicationId) {
        var resultDecision = decisionRepository.findByApplicationId(applicationId)
                .orElseThrow(() -> new BusinessException("İlkin monitorinq nəticəsi qeydə alınmayıb. "));
        if (!resultDecision.isCompliant()) {
            throw new BusinessException("İlkin monitorinq nəticəsi uyğun deyil. ");
        }
    }

    public void existsInitialMonitoringDecisionByApplicationId(Integer applicationId) {
        if (decisionRepository.existsByApplicationId(applicationId)) {
            throw new BusinessException("Müraciət silinə bilməz. İlkin monitorinq nəticəsi mövcuddur.");
        }
    }

}

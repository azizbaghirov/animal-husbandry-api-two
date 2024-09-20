package az.eagro.animalhusbandry.api.service;

import az.eagro.animalhusbandry.api.service.model.FinalMonitoringDecisionStateDTO;
import az.eagro.animalhusbandry.api.service.model.certification.FinalMonitoringDecisionRegisteredDTO;
import az.eagro.animalhusbandry.business.monitoring.FinalMonitoringDecisionManager;
import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringDecisionEntity;
import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringDecisionState;
import com.remondis.remap.Mapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FinalMonitoringDecisionService {

    private final FinalMonitoringDecisionManager finalDecisionManager;
    private final Mapper<FinalMonitoringDecisionEntity, FinalMonitoringDecisionRegisteredDTO> finalMonitoringDecisionRegisteredModelDTOMapper;
    private final FinalMonitoringDecisionCancellationService decisionCancellationService;

    public Optional<FinalMonitoringDecisionRegisteredDTO> findByCertificationApplicationId(Integer certificationApplicationId) {
        return finalDecisionManager.findByCertificationApplicationId(certificationApplicationId)
                .map(decision -> {
                    FinalMonitoringDecisionRegisteredDTO decisionDto = finalMonitoringDecisionRegisteredModelDTOMapper.map(decision);
                    decisionDto.setPermitDecisionCancellation(decisionCancellationService.hasPermitCancellationFinalMonitoringDecision(decision));
                    return decisionDto;
                });
    }

    public FinalMonitoringDecisionStateDTO getFinalMonitoringDecisionState(Integer certificationApplicationId) {
        FinalMonitoringDecisionState decisionState = finalDecisionManager.getFinalMonitoringDecisionState(certificationApplicationId);

        return FinalMonitoringDecisionStateDTO.builder()
                .state(decisionState)
                .build();
    }
}

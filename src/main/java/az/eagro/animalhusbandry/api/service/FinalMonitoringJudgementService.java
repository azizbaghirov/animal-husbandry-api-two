package az.eagro.animalhusbandry.api.service;

import az.eagro.animalhusbandry.api.service.model.OperatorsJudgementStateDTO;
import az.eagro.animalhusbandry.api.service.model.monitoring.FinalMonitoringJudgementDTO;
import az.eagro.animalhusbandry.api.service.model.monitoring.ModifiedFinalMonitoringJudgementDTO;
import az.eagro.animalhusbandry.api.service.model.monitoring.NewFinalMonitoringJudgementDTO;
import az.eagro.animalhusbandry.business.monitoring.FinalMonitoringJudgementManager;
import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringJudgementEntity;
import com.remondis.remap.Mapper;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FinalMonitoringJudgementService {

    private final FinalMonitoringJudgementManager finalMonitoringJudgementManager;
    private final Mapper<FinalMonitoringJudgementEntity, FinalMonitoringJudgementDTO> finalMonitoringJudgementModelDTOMapper;
    private final Mapper<NewFinalMonitoringJudgementDTO, FinalMonitoringJudgementEntity> newFinalMonitoringJudgementModelDTOMapper;
    private final Mapper<ModifiedFinalMonitoringJudgementDTO, FinalMonitoringJudgementEntity> modifiedFinalMonitoringJudgementDTOModelMapper;

    public FinalMonitoringJudgementDTO getById(UUID finalMonitoringJudgementId) {
        FinalMonitoringJudgementEntity finalMonitoringJudgement = finalMonitoringJudgementManager.getById(finalMonitoringJudgementId);

        return finalMonitoringJudgementModelDTOMapper.map(finalMonitoringJudgement);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public FinalMonitoringJudgementDTO create(NewFinalMonitoringJudgementDTO newFinalMonitoringJudgementDTO) {
        FinalMonitoringJudgementEntity newFinalMonitoringJudgement = newFinalMonitoringJudgementModelDTOMapper.map(newFinalMonitoringJudgementDTO);
        FinalMonitoringJudgementEntity finalMonitoringJudgement = finalMonitoringJudgementManager.create(newFinalMonitoringJudgement);

        return finalMonitoringJudgementModelDTOMapper.map(finalMonitoringJudgement);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public FinalMonitoringJudgementDTO update(ModifiedFinalMonitoringJudgementDTO modifiedFinalMonitoringJudgementDTO) {
        UUID judgementId = modifiedFinalMonitoringJudgementDTO.getId();

        FinalMonitoringJudgementEntity judgement = finalMonitoringJudgementManager.getById(judgementId);
        FinalMonitoringJudgementEntity modifiedJudgement = modifiedFinalMonitoringJudgementDTOModelMapper
                .map(modifiedFinalMonitoringJudgementDTO, judgement);
        FinalMonitoringJudgementEntity updatedJudgement = finalMonitoringJudgementManager.update(modifiedJudgement);

        return finalMonitoringJudgementModelDTOMapper.map(updatedJudgement);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void deleteById(UUID finalMonitoringJudgementId) {
        finalMonitoringJudgementManager.deleteById(finalMonitoringJudgementId);
    }

    public OperatorsJudgementStateDTO existsOwnJudgement(Integer applicationId) {
        return finalMonitoringJudgementManager.existsOwnJudgement(applicationId);
    }
}

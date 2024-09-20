package az.eagro.animalhusbandry.api.service.mapper.monitoring;

import az.eagro.animalhusbandry.api.service.model.OperatorSummaryDTO;
import az.eagro.animalhusbandry.api.service.model.monitoring.FinalMonitoringJudgementDTO;
import az.eagro.animalhusbandry.api.service.model.monitoring.ModifiedFinalMonitoringJudgementDTO;
import az.eagro.animalhusbandry.api.service.model.monitoring.NewFinalMonitoringJudgementDTO;
import az.eagro.animalhusbandry.model.OperatorEntity;
import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringJudgementEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration

@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FinalMonitoringJudgementModelDTOMapperConfig {

    private final Mapper<OperatorEntity, OperatorSummaryDTO> operatorSummaryToDtoMapper;

    @Bean
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Mapper<FinalMonitoringJudgementEntity, FinalMonitoringJudgementDTO> finalMonitoringJudgementModelDTOMapper() {
        return Mapping.from(FinalMonitoringJudgementEntity.class)
                .to(FinalMonitoringJudgementDTO.class)
                .omitInSource(FinalMonitoringJudgementEntity::getCertificationApplicationId)
                .omitInSource(FinalMonitoringJudgementEntity::getModifiedAt)
                .omitInSource(FinalMonitoringJudgementEntity::getDeletedAt)
                .omitInSource(FinalMonitoringJudgementEntity::getInvalidatedAt)
                .reassign(FinalMonitoringJudgementEntity::getCreatedBy).to(FinalMonitoringJudgementDTO::getAuthor)
                .useMapper(operatorSummaryToDtoMapper)
                .omitOthers()
                .mapper();

    }

    @Bean
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Mapper<NewFinalMonitoringJudgementDTO, FinalMonitoringJudgementEntity> newFinalMonitoringJudgementModelDTOMapper() {
        return Mapping.from(NewFinalMonitoringJudgementDTO.class)
                .to(FinalMonitoringJudgementEntity.class)
                .omitInDestination(FinalMonitoringJudgementEntity::getId)
                .omitInDestination(FinalMonitoringJudgementEntity::getCreatedBy)
                .omitInDestination(FinalMonitoringJudgementEntity::getCreatedAt)
                .omitInDestination(FinalMonitoringJudgementEntity::getModifiedAt)
                .omitInDestination(FinalMonitoringJudgementEntity::getDeletedAt)
                .omitInDestination(FinalMonitoringJudgementEntity::getInvalidatedAt)
                .omitInSource(NewFinalMonitoringJudgementDTO::getJustification)
                .set(FinalMonitoringJudgementEntity::getJustification)
                .with(dto -> dto.getJustification().trim())
                .mapper();
    }

    @Bean
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Mapper<ModifiedFinalMonitoringJudgementDTO, FinalMonitoringJudgementEntity> modifiedFinalMonitoringJudgementDTOModelMapper() {
        return Mapping.from(ModifiedFinalMonitoringJudgementDTO.class)
                .to(FinalMonitoringJudgementEntity.class)
                .omitInSource(ModifiedFinalMonitoringJudgementDTO::getId)
                .omitInSource(ModifiedFinalMonitoringJudgementDTO::getJustification)
                .omitInDestination(FinalMonitoringJudgementEntity::getCreatedBy)
                .omitInDestination(FinalMonitoringJudgementEntity::getId)
                .omitInDestination(FinalMonitoringJudgementEntity::getCreatedAt)
                .omitInDestination(FinalMonitoringJudgementEntity::getModifiedAt)
                .omitInDestination(FinalMonitoringJudgementEntity::getDeletedAt)
                .omitInDestination(FinalMonitoringJudgementEntity::getInvalidatedAt)
                .omitInDestination(FinalMonitoringJudgementEntity::getCertificationApplicationId)
                .set(FinalMonitoringJudgementEntity::getJustification)
                .with(dto -> dto.getJustification().trim())
                .mapper();
    }
}

package az.eagro.animalhusbandry.api.service.mapper.monitoring;

import az.eagro.animalhusbandry.api.service.model.NewDeniedFinalMonitoringDecisionDTO;
import az.eagro.animalhusbandry.api.service.model.NewPermittedFinalMonitoringDecisionDTO;
import az.eagro.animalhusbandry.api.service.model.OperatorSummaryDTO;
import az.eagro.animalhusbandry.api.service.model.certification.FileDTO;
import az.eagro.animalhusbandry.api.service.model.certification.FinalMonitoringDecisionDTO;
import az.eagro.animalhusbandry.api.service.model.certification.FinalMonitoringDecisionRegisteredDTO;
import az.eagro.animalhusbandry.model.FileEntity;
import az.eagro.animalhusbandry.model.OperatorEntity;
import az.eagro.animalhusbandry.model.monitoring.FinalMonitoringDecisionEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FinalMonitoringDecisionModelDTOMapperConfig {

    private final Mapper<FileEntity, FileDTO> fileToDtoMapper;
    private final Mapper<OperatorEntity, OperatorSummaryDTO> operatorSummaryToDtoMapper;

    @Bean
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Mapper<FinalMonitoringDecisionEntity, FinalMonitoringDecisionRegisteredDTO> finalMonitoringDecisionRegisteredModelDTOMapper() {
        return Mapping.from(FinalMonitoringDecisionEntity.class)
                .to(FinalMonitoringDecisionRegisteredDTO.class)
                .omitInSource(FinalMonitoringDecisionEntity::getRegisteredAt)
                .omitInSource(FinalMonitoringDecisionEntity::getInvalidatedBy)
                .omitInSource(FinalMonitoringDecisionEntity::getInvalidatedAt)
                .omitInDestination(FinalMonitoringDecisionRegisteredDTO::isPermitDecisionCancellation)
                .useMapper(operatorSummaryToDtoMapper)
                .useMapper(fileToDtoMapper)
                .mapper();
    }

    @Bean
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Mapper<FinalMonitoringDecisionEntity, FinalMonitoringDecisionDTO> finalMonitoringDecisionModelDTOMapper() {
        return Mapping.from(FinalMonitoringDecisionEntity.class)
                .to(FinalMonitoringDecisionDTO.class)
                .omitInSource(FinalMonitoringDecisionEntity::getCreatedBy)
                .omitInSource(FinalMonitoringDecisionEntity::getCreatedAt)
                .omitInSource(FinalMonitoringDecisionEntity::getInvalidatedBy)
                .omitInSource(FinalMonitoringDecisionEntity::getInvalidatedAt)
                .omitInSource(FinalMonitoringDecisionEntity::getFiles)
                .omitInSource(FinalMonitoringDecisionEntity::getRegisteredAt)
                .omitOthers()
                .mapper();
    }

    @Bean
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Mapper<NewPermittedFinalMonitoringDecisionDTO, FinalMonitoringDecisionEntity> newPermittedFinalMonitoringDecisionModelDTOMapper() {
        return Mapping.from(NewPermittedFinalMonitoringDecisionDTO.class)
                .to(FinalMonitoringDecisionEntity.class)
                .omitInSource(NewPermittedFinalMonitoringDecisionDTO::getFiles)
                .omitInSource(NewPermittedFinalMonitoringDecisionDTO::getCertificateFile)
                .omitInDestination(FinalMonitoringDecisionEntity::getId)
                .omitInDestination(FinalMonitoringDecisionEntity::getCreatedBy)
                .omitInDestination(FinalMonitoringDecisionEntity::getCreatedAt)
                .omitInDestination(FinalMonitoringDecisionEntity::getInvalidatedAt)
                .omitInDestination(FinalMonitoringDecisionEntity::getInvalidatedBy)
                .omitInDestination(FinalMonitoringDecisionEntity::getFiles)
                .omitInDestination(FinalMonitoringDecisionEntity::getCertified)
                .mapper();
    }

    @Bean
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Mapper<NewDeniedFinalMonitoringDecisionDTO, FinalMonitoringDecisionEntity> newDeniedFinalMonitoringDecisionModelDTOMapper() {
        return Mapping.from(NewDeniedFinalMonitoringDecisionDTO.class)
                .to(FinalMonitoringDecisionEntity.class)
                .omitOthers()
                .mapper();
    }

}

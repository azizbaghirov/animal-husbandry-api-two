package az.eagro.animalhusbandry.api.service.mapper;

import az.eagro.animalhusbandry.api.service.model.ApplicationInitialMonitoringDecisionDTO;
import az.eagro.animalhusbandry.api.service.model.FarmSpecializationDTO;
import az.eagro.animalhusbandry.api.service.model.InitialMonitoringDecisionDTO;
import az.eagro.animalhusbandry.api.service.model.NewInitialMonitoringDecisionDTO;
import az.eagro.animalhusbandry.api.service.model.OperatorSummaryDTO;
import az.eagro.animalhusbandry.model.BreedingAnimalFarmSpecializationEntity;
import az.eagro.animalhusbandry.model.CertificationApplicationEntity;
import az.eagro.animalhusbandry.model.FileEntity;
import az.eagro.animalhusbandry.model.InitialMonitoringDecisionEntity;
import az.eagro.animalhusbandry.model.OperatorEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class InitialMonitoringDecisionModelDTOMapperConfig {

    private final Mapper<CertificationApplicationEntity, ApplicationInitialMonitoringDecisionDTO> applicationInitialMonitoringDecisionModelDTOMapper;
    private final Mapper<OperatorEntity, OperatorSummaryDTO> operatorSummaryToDtoMapper;

    @Bean
    public Mapper<NewInitialMonitoringDecisionDTO, InitialMonitoringDecisionEntity> newInitialMonitoringDecisionModelMapper() {
        return Mapping.from(NewInitialMonitoringDecisionDTO.class)
                .to(InitialMonitoringDecisionEntity.class)
                .omitInDestination(InitialMonitoringDecisionEntity::getFiles)
                .omitInSource(NewInitialMonitoringDecisionDTO::getJustification)
                .set(InitialMonitoringDecisionEntity::getApplication)
                .with(dto -> CertificationApplicationEntity.builder().id(dto.getApplicationId()).build())
                .set(InitialMonitoringDecisionEntity::getJustification)
                .with(dto -> dto.getJustification().trim())
                .omitOthers()
                .mapper();
    }

    @Bean
    public Mapper<InitialMonitoringDecisionEntity, InitialMonitoringDecisionDTO> initialMonitoringDecisionToDtoMapper() {
        return Mapping.from(InitialMonitoringDecisionEntity.class)
                .to(InitialMonitoringDecisionDTO.class)
                .set(InitialMonitoringDecisionDTO::getFileIds)
                .with(entity -> entity.getFiles().stream().map(FileEntity::getId).collect(Collectors.toList()))
                .useMapper(applicationInitialMonitoringDecisionModelDTOMapper)
                .useMapper(operatorSummaryToDtoMapper)
                .omitOthers()
                .mapper();
    }


}

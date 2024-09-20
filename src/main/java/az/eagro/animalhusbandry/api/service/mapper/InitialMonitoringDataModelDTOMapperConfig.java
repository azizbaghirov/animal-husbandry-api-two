package az.eagro.animalhusbandry.api.service.mapper;

import az.eagro.animalhusbandry.api.service.model.DiscoveredDataDTO;
import az.eagro.animalhusbandry.api.service.model.InitialMonitoringDataDTO;
import az.eagro.animalhusbandry.api.service.model.NewDiscoveredDataDTO;
import az.eagro.animalhusbandry.api.service.model.NewInitialMonitoringDataDTO;
import az.eagro.animalhusbandry.api.service.model.NewInitialMonitoringDecisionDTO;
import az.eagro.animalhusbandry.model.DiscoveredDataEntity;
import az.eagro.animalhusbandry.model.InitialMonitoringDataEntity;
import az.eagro.animalhusbandry.model.InitialMonitoringDecisionEntity;
import az.eagro.animalhusbandry.model.VaccinationEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class InitialMonitoringDataModelDTOMapperConfig {

    private final Mapper<NewInitialMonitoringDecisionDTO, InitialMonitoringDecisionEntity> newInitialMonitoringDecisionModelMapper;
    private final Mapper<NewDiscoveredDataDTO, DiscoveredDataEntity> newDiscoveredDataModelMapper;
    private final Mapper<DiscoveredDataEntity, DiscoveredDataDTO> discoveredDataToDtoMapper;


    @Bean
    public Mapper<NewInitialMonitoringDataDTO, InitialMonitoringDataEntity> newInitialMonitoringDataModelMapper() {
        return Mapping.from(NewInitialMonitoringDataDTO.class)
                .to(InitialMonitoringDataEntity.class)
                .set(InitialMonitoringDataEntity::getVaccinations)
                .with(dto -> {
                    Set<VaccinationEntity> vaccinations = new HashSet<>();
                    dto.getVaccinationIds().forEach(vaccinationId ->
                            vaccinations.add(VaccinationEntity.builder().id(vaccinationId).build()));
                    return vaccinations;
                })
                .useMapper(newInitialMonitoringDecisionModelMapper)
                .useMapper(newDiscoveredDataModelMapper)
                .omitOthers()
                .mapper();
    }

    @Bean
    public Mapper<InitialMonitoringDataEntity, InitialMonitoringDataDTO> initialMonitoringDataToDtoMapper() {
        return Mapping.from(InitialMonitoringDataEntity.class)
                .to(InitialMonitoringDataDTO.class)
                .omitInDestination(InitialMonitoringDataDTO::getMonitoringDecision)
                .omitInDestination(InitialMonitoringDataDTO::getVaccinations)
                .useMapper(discoveredDataToDtoMapper)
                .omitOthers()
                .mapper();
    }

}
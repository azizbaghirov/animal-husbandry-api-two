package az.eagro.animalhusbandry.api.service.mapper;

import az.eagro.animalhusbandry.api.service.model.AnimalSortDTO;
import az.eagro.animalhusbandry.api.service.model.AnimalTypeDTO;
import az.eagro.animalhusbandry.api.service.model.ApplicationDTO;
import az.eagro.animalhusbandry.api.service.model.ApplicationInitialMonitoringDTO;
import az.eagro.animalhusbandry.api.service.model.ApplicationInitialMonitoringDecisionDTO;
import az.eagro.animalhusbandry.api.service.model.ApplicationOperatorDTO;
import az.eagro.animalhusbandry.api.service.model.ApplicationSummaryDTO;
import az.eagro.animalhusbandry.api.service.model.BreedingAnimalFarmInitialMonitoringDecisionDTO;
import az.eagro.animalhusbandry.api.service.model.BreedingAnimalFarmSummaryDTO;
import az.eagro.animalhusbandry.api.service.model.FarmSummaryDTO;
import az.eagro.animalhusbandry.api.service.model.FarmerAccountDTO;
import az.eagro.animalhusbandry.api.service.model.NewApplicationDTO;
import az.eagro.animalhusbandry.api.service.model.PhysicalPersonDTO;
import az.eagro.animalhusbandry.model.BreedingAnimalFarmEntity;
import az.eagro.animalhusbandry.model.CertificationApplicationEntity;
import az.eagro.animalhusbandry.model.FarmerAccountEntity;
import az.eagro.animalhusbandry.model.InitialMonitoringStatus;
import az.eagro.animalhusbandry.model.PhysicalPersonEntity;
import az.eagro.animalhusbandry.util.timeutil.TimeValidation;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class ApplicationModelDTOMapperConfig {

    private final Mapper<FarmerAccountEntity, FarmerAccountDTO> farmerAccountToDtoMapper;
    private final Mapper<BreedingAnimalFarmEntity, FarmSummaryDTO> farmModelDtoMapper;
    private final Mapper<PhysicalPersonEntity, PhysicalPersonDTO> physicalPersonToDtoMapper;
    private final Mapper<BreedingAnimalFarmEntity, BreedingAnimalFarmInitialMonitoringDecisionDTO> farmInitialMonitoringDecisionModelDtoMapper;
    private final Mapper<BreedingAnimalFarmEntity, BreedingAnimalFarmSummaryDTO> breedingAnimalFarmToDtoMapper;


    @Bean
    public Mapper<CertificationApplicationEntity, ApplicationSummaryDTO> applicationSummaryModelDTOMapper() {
        return Mapping.from(CertificationApplicationEntity.class)
                .to(ApplicationSummaryDTO.class)
                .set(ApplicationSummaryDTO::getAdministrativeArea)
                .with(entity ->
                        entity.getFarm().getAdministrativeArea().getName())
                .set(ApplicationSummaryDTO::getFarmSpecialization)
                .with(entity ->
                        entity.getFarm().getFarmSpecialization() == null ? null : entity.getFarm().getFarmSpecialization().getName())
                .set(ApplicationSummaryDTO::getAnimalType)
                .with(entity ->
                        entity.getFarm().getAnimalType().getName())
                .set(ApplicationSummaryDTO::getAnimalSort)
                .with(entity ->
                        entity.getFarm().getAnimalSort().getName())
                .set(ApplicationSummaryDTO::isPermitDeletionApplication)
                .with(certificationApplication -> permitDeletion(certificationApplication))
                .omitOthers()
                .mapper();
    }

    private boolean permitDeletion(CertificationApplicationEntity certificationApplication) {
        return certificationApplication.getInitialMonitoringStatus() == InitialMonitoringStatus.UNDEFINED
                && TimeValidation.twentyFourHoursPassed(certificationApplication.getCreatedAt());
    }

    @Bean
    public Mapper<CertificationApplicationEntity, ApplicationOperatorDTO> applicationOperatorModelDTOMapper() {
        return Mapping.from(CertificationApplicationEntity.class)
                .to(ApplicationOperatorDTO.class)
                .useMapper(farmModelDtoMapper)
                .useMapper(farmerAccountToDtoMapper)
                .omitOthers()
                .mapper();
    }

    @Bean
    public Mapper<CertificationApplicationEntity, ApplicationInitialMonitoringDTO> applicationInitialMonitoringModelDTOMapper() {
        return Mapping.from(CertificationApplicationEntity.class)
                .to(ApplicationInitialMonitoringDTO.class)
                .omitOthers()
                .mapper();
    }

    @Bean
    public Mapper<CertificationApplicationEntity, ApplicationInitialMonitoringDecisionDTO> applicationInitialMonitoringDecisionModelDTOMapper() {
        return Mapping.from(CertificationApplicationEntity.class)
                .to(ApplicationInitialMonitoringDecisionDTO.class)
                .useMapper(farmerAccountToDtoMapper)
                .useMapper(physicalPersonToDtoMapper)
                .useMapper(farmInitialMonitoringDecisionModelDtoMapper)
                .omitOthers()
                .mapper();
    }

    @Bean
    public Mapper<NewApplicationDTO, CertificationApplicationEntity> newApplicationModelMapper() {
        return Mapping.from(NewApplicationDTO.class)
                .to(CertificationApplicationEntity.class)
                .omitInDestination(CertificationApplicationEntity::getId)
                .omitInDestination(CertificationApplicationEntity::getApplicationStatus)
                .omitInDestination(CertificationApplicationEntity::getCreatedBy)
                .omitInDestination(CertificationApplicationEntity::getCreatedAt)
                .omitInDestination(CertificationApplicationEntity::getFilePath)
                .set(CertificationApplicationEntity::getFarm)
                .with(dto -> BreedingAnimalFarmEntity.builder().id(dto.getFarmId()).build())
                .omitOthers()
                .mapper();
    }

    @Bean
    public Mapper<CertificationApplicationEntity, ApplicationDTO> applicationToDtoMapper() {
        return Mapping.from(CertificationApplicationEntity.class)
                .to(ApplicationDTO.class)
                .set(ApplicationDTO::getVeterinaryContract)
                .with(entity -> entity.getFilePath())
                .set(ApplicationDTO::getAnimalType)
                .with(entity -> AnimalTypeDTO.builder().id(entity.getFarm().getAnimalType().getId())
                        .name(entity.getFarm().getAnimalType().getName()).build())
                .set(ApplicationDTO::getAnimalSort)
                .with(entity -> AnimalSortDTO.builder().id(entity.getFarm().getAnimalSort().getId())
                        .name(entity.getFarm().getAnimalSort().getName()).build())
                .useMapper(farmerAccountToDtoMapper)
                .useMapper(physicalPersonToDtoMapper)
                .useMapper(breedingAnimalFarmToDtoMapper)
                .omitOthers()
                .mapper();
    }

}

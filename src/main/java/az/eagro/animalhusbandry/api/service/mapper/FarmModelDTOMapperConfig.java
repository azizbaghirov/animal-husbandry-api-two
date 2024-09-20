package az.eagro.animalhusbandry.api.service.mapper;

import az.eagro.animalhusbandry.api.service.model.AdministrativeAreaDTO;
import az.eagro.animalhusbandry.api.service.model.AnimalSortDTO;
import az.eagro.animalhusbandry.api.service.model.AnimalTypeDTO;
import az.eagro.animalhusbandry.api.service.model.BreedingAnimalFarmDTO;
import az.eagro.animalhusbandry.api.service.model.BreedingAnimalFarmInitialMonitoringDecisionDTO;
import az.eagro.animalhusbandry.api.service.model.BreedingAnimalFarmSummaryDTO;
import az.eagro.animalhusbandry.api.service.model.BreedingAnimalFarmTypeDTO;
import az.eagro.animalhusbandry.api.service.model.FarmSpecializationDTO;
import az.eagro.animalhusbandry.api.service.model.FarmSummaryDTO;
import az.eagro.animalhusbandry.api.service.model.FieldDocumentSummaryDTO;
import az.eagro.animalhusbandry.api.service.model.NewFarmDTO;
import az.eagro.animalhusbandry.api.service.model.RegionDTO;
import az.eagro.animalhusbandry.model.AdministrativeAreaEntity;
import az.eagro.animalhusbandry.model.AnimalSortEntity;
import az.eagro.animalhusbandry.model.AnimalTypeEntity;
import az.eagro.animalhusbandry.model.BreedingAnimalFarmEntity;
import az.eagro.animalhusbandry.model.BreedingAnimalFarmSpecializationEntity;
import az.eagro.animalhusbandry.model.BreedingAnimalFarmTypeEntity;
import az.eagro.animalhusbandry.model.FieldDocumentEntity;
import az.eagro.animalhusbandry.model.RegionEntity;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FarmModelDTOMapperConfig {

    private final Mapper<RegionEntity, RegionDTO> regionModelDTOMapper;
    private final Mapper<FieldDocumentEntity, FieldDocumentSummaryDTO> fieldDocumentSummaryModelDTOMapper;
    private final Mapper<AdministrativeAreaEntity, AdministrativeAreaDTO> administrativeAreaToDtoModelMapper;
    private final Mapper<BreedingAnimalFarmSpecializationEntity, FarmSpecializationDTO> farmSpecializationModelDTOMapper;
    private final Mapper<BreedingAnimalFarmTypeEntity, BreedingAnimalFarmTypeDTO> breedingAnimalFarmTypeToDtoModelMapper;
    private final Mapper<AnimalTypeEntity, AnimalTypeDTO> animalTypeModelDTOMapper;
    private final Mapper<AnimalSortEntity, AnimalSortDTO> animalSortModelDTOMapper;

    @Bean
    public Mapper<NewFarmDTO, BreedingAnimalFarmEntity> newFarmModelMapper() {
        return Mapping.from(NewFarmDTO.class)
                .to(BreedingAnimalFarmEntity.class)
                .omitInDestination(BreedingAnimalFarmEntity::getId)
                .omitInDestination(BreedingAnimalFarmEntity::getCreatedBy)
                .omitInDestination(BreedingAnimalFarmEntity::getCreatedAt)
                .omitInSource(NewFarmDTO::getFarmTypeId)
                .omitInSource(NewFarmDTO::getFarmSpecializationId)
                .omitInSource(NewFarmDTO::getAdministrativeAreaId)
                .omitInSource(NewFarmDTO::getRegionId)
                .omitInSource(NewFarmDTO::getActIds)
                .set(BreedingAnimalFarmEntity::getFarmSpecialization)
                .with(dto -> dto.getFarmSpecializationId() == null ? null : BreedingAnimalFarmSpecializationEntity.builder()
                        .id(dto.getFarmSpecializationId()).build())
                .set(BreedingAnimalFarmEntity::getFarmType)
                .with(dto -> BreedingAnimalFarmTypeEntity.builder().id(dto.getFarmTypeId()).build())
                .set(BreedingAnimalFarmEntity::getAnimalType)
                .with(dto -> AnimalTypeEntity.builder().id(dto.getAnimalTypeId()).build())
                .set(BreedingAnimalFarmEntity::getAnimalSort)
                .with(dto -> AnimalSortEntity.builder().id(dto.getAnimalSortId()).build())
                .set(BreedingAnimalFarmEntity::getAdministrativeArea)
                .with(dto -> AdministrativeAreaEntity.builder().id(dto.getAdministrativeAreaId())
                        .region(RegionEntity.builder().id(dto.getRegionId()).build()).build())
                .omitOthers()
                .mapper();
    }

    @Bean
    public Mapper<BreedingAnimalFarmEntity, BreedingAnimalFarmDTO> farmToDtoMapper() {
        return Mapping.from(BreedingAnimalFarmEntity.class)
                .to(BreedingAnimalFarmDTO.class)
                .useMapper(farmSpecializationModelDTOMapper)
                .useMapper(animalTypeModelDTOMapper)
                .useMapper(animalSortModelDTOMapper)
                .set(BreedingAnimalFarmDTO::getAdministrativeArea)
                .with(e -> AdministrativeAreaDTO.builder().id(e.getAdministrativeArea().getId()).name(e.getAdministrativeArea().getName()).build())
                .omitOthers()
                .mapper();
    }

    @Bean
    public Mapper<BreedingAnimalFarmEntity, BreedingAnimalFarmSummaryDTO> breedingAnimalFarmToDtoMapper() {
        return Mapping.from(BreedingAnimalFarmEntity.class)
                .to(BreedingAnimalFarmSummaryDTO.class)
                .useMapper(regionModelDTOMapper)
                .useMapper(farmSpecializationModelDTOMapper)
                .useMapper(administrativeAreaToDtoModelMapper)
                .useMapper(breedingAnimalFarmTypeToDtoModelMapper)
                .useMapper(fieldDocumentSummaryModelDTOMapper)
                .omitOthers()
                .mapper();
    }

    @Bean
    public Mapper<BreedingAnimalFarmEntity, FarmSummaryDTO> farmModelDtoMapper() {
        return Mapping.from(BreedingAnimalFarmEntity.class)
                .to(FarmSummaryDTO.class)
                .useMapper(regionModelDTOMapper)
                .useMapper(administrativeAreaToDtoModelMapper)
                .useMapper(breedingAnimalFarmTypeToDtoModelMapper)
                .useMapper(animalTypeModelDTOMapper)
                .useMapper(animalSortModelDTOMapper)
                .omitOthers()
                .mapper();
    }

    @Bean
    public Mapper<BreedingAnimalFarmEntity, BreedingAnimalFarmInitialMonitoringDecisionDTO> farmInitialMonitoringDecisionModelDtoMapper() {
        return Mapping.from(BreedingAnimalFarmEntity.class)
                .to(BreedingAnimalFarmInitialMonitoringDecisionDTO.class)
                .useMapper(regionModelDTOMapper)
                .useMapper(administrativeAreaToDtoModelMapper)
                .useMapper(breedingAnimalFarmTypeToDtoModelMapper)
                .useMapper(farmSpecializationModelDTOMapper)
                .omitOthers()
                .mapper();
    }
}
